package com.chat.demo.service.rag.impl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chat.demo.dto.BrandingFile;
import com.chat.demo.dto.OrganizationRequest;
import com.chat.demo.mapper.OrganizationMapper;
import com.chat.demo.model.Organization;
import com.chat.demo.repository.OrganizationRepository;
import com.chat.demo.service.rag.OrganizationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{

	private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	private static final String BRANDING_DIR = "uploads/branding/";

	private static final Map<String, String> EXTENSION_BY_CONTENT_TYPE = Map.of(
			"image/png", "png",
			"image/jpeg", "jpg",
			"image/svg+xml", "svg",
			"image/webp", "webp",
			"image/x-icon", "ico",
			"image/vnd.microsoft.icon", "ico"
	);

	private static final Map<String, String> CONTENT_TYPE_BY_EXTENSION = Map.of(
			"png", "image/png",
			"jpg", "image/jpeg",
			"jpeg", "image/jpeg",
			"svg", "image/svg+xml",
			"webp", "image/webp",
			"ico", "image/x-icon"
	);

	private final OrganizationRepository organRepo;
	private final OrganizationMapper  mapper;

	@Override
	public OrganizationRequest save(OrganizationRequest organization) {
		
		
		  Organization entity =
	                mapper.toEntity(organization);

	        entity.setCreatedAt(LocalDateTime.now());
	        entity.setUpdatedAt(LocalDateTime.now());

	        Organization saved =
	                organRepo.save(entity);

	        return mapper.toResponse(saved);
		
		
	}

	@Override
	@Transactional
	public OrganizationRequest update(Long id, OrganizationRequest organization) {
		  Organization existing = organRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Organization not found"));

	        // logoUrl/faviconUrl NO se tocan acá: los administra exclusivamente uploadLogo/
	        // uploadFavicon, para que este PUT nunca pise la URL servida por el backend.
	        existing.setName(organization.getName());
	        existing.setPrimaryColor(organization.getPrimaryColor());
	        existing.setSecondaryColor(organization.getSecondaryColor());
	        existing.setDomain(organization.getDomain());
	        existing.setSupportEmail(organization.getSupportEmail());
	        existing.setUpdatedAt(LocalDateTime.now());

	        Organization saved = organRepo.save(existing);

	        return mapper.toResponse(saved);
	}

	@Override
	public Optional<OrganizationRequest> findById(Long id) {
		 return organRepo.findById(id) .map(mapper::toResponse);
	}

	@Override
	public Optional<OrganizationRequest> findByName(String name) {
		return organRepo.findByName(name) .map(mapper::toResponse);
	}

	@Override
	public Optional<OrganizationRequest> findByDomain(String domain) {
	return organRepo.findByDomain(domain) .map(mapper::toResponse);
	}

	@Override
	public List<OrganizationRequest> findAll() {
		return organRepo.findAll().stream()
	            .map(mapper::toResponse)
	            .toList();
		}

	@Override
	public void delete(Long id) {
		 organRepo.deleteById(id);

	}

	private OrganizationRequest uploadBrandingAsset(Long id, String kind, MultipartFile file) {
		Organization org = organRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Organization not found"));

		if (file == null || file.isEmpty()) {
			throw new RuntimeException("Empty file");
		}
		String extension = EXTENSION_BY_CONTENT_TYPE.get(file.getContentType());
		if (extension == null) {
			throw new RuntimeException("Unsupported image type: " + file.getContentType());
		}

		try {
			Path dir = Paths.get(BRANDING_DIR);
			Files.createDirectories(dir);

			// Borra cualquier archivo previo de este mismo kind (puede tener otra extensión
			// si la imagen anterior era de otro formato) antes de guardar el nuevo.
			String prefix = "org_" + id + "_" + kind;
			try (var existing = Files.list(dir)) {
				existing.filter(p -> p.getFileName().toString().startsWith(prefix))
						.forEach(p -> {
							try {
								Files.deleteIfExists(p);
							} catch (IOException e) {
								log.warn("Could not delete previous branding file {}: {}", p, e.getMessage());
							}
						});
			}

			Path target = dir.resolve(prefix + "." + extension);
			Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Could not store " + kind, e);
		}

		String servedPath = "/organizations/" + id + "/" + kind;
		if ("logo".equals(kind)) {
			org.setLogoUrl(servedPath);
		} else {
			org.setFaviconUrl(servedPath);
		}
		org.setUpdatedAt(LocalDateTime.now());
		return mapper.toResponse(organRepo.save(org));
	}

	private Optional<BrandingFile> loadBrandingAsset(Long id, String kind) {
		Path dir = Paths.get(BRANDING_DIR);
		if (!Files.isDirectory(dir)) {
			return Optional.empty();
		}
		String prefix = "org_" + id + "_" + kind;
		try (var stream = Files.list(dir)) {
			Optional<Path> found = stream
					.filter(p -> p.getFileName().toString().startsWith(prefix))
					.findFirst();
			if (found.isEmpty()) {
				return Optional.empty();
			}
			Path path = found.get();
			String fileName = path.getFileName().toString();
			int dot = fileName.lastIndexOf('.');
			String ext = dot == -1 ? "" : fileName.substring(dot + 1).toLowerCase();
			String contentType = CONTENT_TYPE_BY_EXTENSION.getOrDefault(ext, "application/octet-stream");
			return Optional.of(new BrandingFile(Files.readAllBytes(path), contentType));
		} catch (IOException e) {
			throw new RuntimeException("Could not read " + kind, e);
		}
	}

	@Override
	public OrganizationRequest uploadLogo(Long id, MultipartFile file) {
		return uploadBrandingAsset(id, "logo", file);
	}

	@Override
	public OrganizationRequest uploadFavicon(Long id, MultipartFile file) {
		return uploadBrandingAsset(id, "favicon", file);
	}

	@Override
	public Optional<BrandingFile> loadLogo(Long id) {
		return loadBrandingAsset(id, "logo");
	}

	@Override
	public Optional<BrandingFile> loadFavicon(Long id) {
		return loadBrandingAsset(id, "favicon");
	}

}
