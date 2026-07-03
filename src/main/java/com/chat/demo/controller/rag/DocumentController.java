package com.chat.demo.controller.rag;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.chat.demo.dto.DocumentRequest;
import com.chat.demo.dto.DocumentResponse;
import com.chat.demo.model.User;
import com.chat.demo.service.auth.CustomUserDetails;

import com.chat.demo.service.rag.DocumentService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

	private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
	private final DocumentService docServ;

	private User currentUser(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();
		if (user.getOrganization() == null) {
			throw new RuntimeException("Authenticated user has no organization assigned");
		}
		return user;
	}

	private boolean hasRole(User user, String roleName) {
		return user.getRoles().stream().anyMatch(r -> roleName.equals(r.getName()));
	}

	// Areas que un ADMIN general puede tocar sin restricción (null); un AREA_ADMIN
	// queda limitado a su propia área.
	// Filtra a lo que la organización del caller puede ver: ningún endpoint de lectura
	// confía en un id que venga del cliente (path variable) sin este chequeo.
	private List<DocumentResponse> scopedToCaller(List<DocumentResponse> docs, Long callerOrganizationId) {
		return docs.stream()
				.filter(d -> callerOrganizationId.equals(d.getOrganizationId()))
				.toList();
	}

	private Long resolveCallerAreaId(User user) {
		if (hasRole(user, "ADMIN")) {
			return null;
		}
		if (user.getArea() == null) {
			throw new AccessDeniedException("AREA_ADMIN user has no area assigned");
		}
		return user.getArea().getId();
	}

	// Un AREA_ADMIN solo puede crear/subir documentos scopeados exactamente a su propia área
	// (no puede dejarlo global ni asignarlo a otra área).
	private void enforceAreaAdminUploadScope(User user, List<Long> areaIds) {
		if (hasRole(user, "ADMIN")) {
			return;
		}
		if (user.getArea() == null) {
			throw new AccessDeniedException("AREA_ADMIN user has no area assigned");
		}
		Long ownAreaId = user.getArea().getId();
		if (areaIds == null || areaIds.size() != 1 || !areaIds.get(0).equals(ownAreaId)) {
			throw new AccessDeniedException("AREA_ADMIN can only upload documents scoped to their own area");
		}
	}

	//crear o guardar una configuracion
		  @PostMapping
		  @PreAuthorize("hasAnyRole('ADMIN','AREA_ADMIN')")
	    public DocumentResponse documentSave(@RequestBody DocumentRequest request, Authentication authentication) {
	        User user = currentUser(authentication);
	        request.setOrganizationId(user.getOrganization().getId());
	        request.setUploadedById(user.getId());
	        enforceAreaAdminUploadScope(user, request.getAreaIds());
	        return docServ.save(request);
	    }

		// modificar una documentacion cargada

		 @PutMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','AREA_ADMIN')")
		 public DocumentResponse documentUpdate (@PathVariable Long id,@RequestBody DocumentRequest request, Authentication authentication) {
			 User user = currentUser(authentication);
			 Long callerAreaId = resolveCallerAreaId(user);
			 return docServ.update(id, request, callerAreaId);
		 }

		 // buscar una documentacion por id

		 @GetMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List<DocumentResponse> foundDocumentationById (@PathVariable Long id, Authentication authentication) {
			 User user = currentUser(authentication);
			 return scopedToCaller(docServ.findById(id), user.getOrganization().getId());
		 }


		// buscar una documentacion por su titulo

		 @GetMapping("/title/{title}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List<DocumentResponse> foundDocumentationByTitle (@PathVariable String title, Authentication authentication) {
			 User user = currentUser(authentication);
			 return scopedToCaller(docServ.findByTitle(title), user.getOrganization().getId());
		 }


		 // obtener la documentacion por organizacion
		  @GetMapping("/organization/{organizationId}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List <DocumentResponse> foundDocumentationByOrganization (@PathVariable Long organizationId, Authentication authentication) {
			 User user = currentUser(authentication);
			 // El organizationId de la URL es un dato del cliente y puede modificarse:
			 // nunca se confía en él sin verificar que sea el de la propia organización.
			 if (!organizationId.equals(user.getOrganization().getId())) {
				 throw new AccessDeniedException("Organization does not belong to the authenticated user");
			 }
			 return docServ.findByOrganization(organizationId);
		 }


		 // obtener documentacion por area (oficinas o especialidades)

		  @GetMapping("/area/{areaId}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List<DocumentResponse> foundDocumentationByArea (@PathVariable Long areaId, Authentication authentication) {
			 User user = currentUser(authentication);
			 return scopedToCaller(docServ.findByArea(areaId), user.getOrganization().getId());
		 }


		 // obtener documentacion cargada por un usuario seleccionado

		  @GetMapping("/user/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List<DocumentResponse> foundDocumentationByUser (@PathVariable Long id, Authentication authentication) {
			 User user = currentUser(authentication);
			 return scopedToCaller(docServ.findByUploadedBy(id), user.getOrganization().getId());
		 }


		 // obtener las documentaciones

		 @GetMapping
		  @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public List <DocumentResponse> getDocumentsAll(Authentication authentication) {
			 User user = currentUser(authentication);
			 return docServ.findByOrganization(user.getOrganization().getId());
		 }

		//eliminar una documentacion

		  @DeleteMapping("/{id}")
		  @PreAuthorize("hasAnyRole('ADMIN','AREA_ADMIN')")
		  public void deleteDocumentation(@PathVariable Long id, Authentication authentication) {
			  User user = currentUser(authentication);
			  Long callerAreaId = resolveCallerAreaId(user);
			  docServ.delete(id, callerAreaId);
		  }


		  @PostMapping("/upload")
		  @PreAuthorize("hasAnyRole('ADMIN','AREA_ADMIN')")
		  public DocumentResponse uploadDocument(
		          @RequestParam("filePath") MultipartFile filePath,
		          @RequestParam("title") String title,
		          @RequestParam(value = "areaIds", required = false) List<Long> areaIds,
		          @RequestParam("statusId") Long statusId,
		          Authentication authentication
		  ) {
			  User user = currentUser(authentication);
			  List<Long> effectiveAreaIds = areaIds == null ? List.of() : areaIds;
			  enforceAreaAdminUploadScope(user, effectiveAreaIds);

			  Long organizationId = user.getOrganization().getId();
			  Long uploadedById = user.getId();

			  log.info("Upload request: title={}, organizationId={}, contentType={}", title, organizationId, filePath.getContentType());
			  if (!"application/pdf".equals(filePath.getContentType())) {
				    throw new RuntimeException("Only PDF files allowed, received: " + filePath.getContentType());
				}

			  if (filePath.getSize() > 10_000_000) {
				    throw new RuntimeException("File too large");
				}

		      return docServ.upload(
		              filePath,
		              title,
		              organizationId,
		              effectiveAreaIds,
		              statusId,
		              uploadedById
		      );
		  }


}
