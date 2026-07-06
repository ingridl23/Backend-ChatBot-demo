package com.chat.demo.controller.rag;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.chat.demo.dto.OrganizationRequest;
import com.chat.demo.model.User;
import com.chat.demo.service.auth.CustomUserDetails;
import com.chat.demo.service.rag.OrganizationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService orgServ;

    private User currentUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getOrganization() == null) {
            throw new RuntimeException("Authenticated user has no organization assigned");
        }
        return user;
    }

    // Un ADMIN/USER solo puede leer o modificar la organización a la que pertenece.
    private void assertOwnership(Long id, Long callerOrganizationId) {
        if (!callerOrganizationId.equals(id)) {
            throw new AccessDeniedException("Organization does not belong to the authenticated user");
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OrganizationRequest saveOrganization(@RequestBody OrganizationRequest org) {
        return orgServ.save(org);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OrganizationRequest updateOrganization(@PathVariable Long id, @RequestBody OrganizationRequest org,
            Authentication authentication) {
        User caller = currentUser(authentication);
        assertOwnership(id, caller.getOrganization().getId());
        return orgServ.update(id, org);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
    public Optional<OrganizationRequest> getOrganizationById(@PathVariable Long id, Authentication authentication) {
        User caller = currentUser(authentication);
        assertOwnership(id, caller.getOrganization().getId());
        return orgServ.findById(id);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
    public Optional<OrganizationRequest> getOrganizationByName(@PathVariable String name, Authentication authentication) {
        User caller = currentUser(authentication);
        if (!name.equals(caller.getOrganization().getName())) {
            throw new AccessDeniedException("Organization does not belong to the authenticated user");
        }
        return orgServ.findByName(name);
    }

    @GetMapping("/domain/{domain}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
    public Optional<OrganizationRequest> getOrganizationByDomain(@PathVariable String domain, Authentication authentication) {
        User caller = currentUser(authentication);
        if (!domain.equals(caller.getOrganization().getDomain())) {
            throw new AccessDeniedException("Organization does not belong to the authenticated user");
        }
        return orgServ.findByDomain(domain);
    }

    // Devuelve únicamente la organización del que llama (no hay caso de uso multi-tenant
    // para listar organizaciones ajenas).
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','AREA_ADMIN')")
    public List<OrganizationRequest> getOrganizations(Authentication authentication) {
        User caller = currentUser(authentication);
        return orgServ.findById(caller.getOrganization().getId())
                .map(List::of)
                .orElse(List.of());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteOrganization(@PathVariable Long id, Authentication authentication) {
        User caller = currentUser(authentication);
        assertOwnership(id, caller.getOrganization().getId());
        orgServ.delete(id);
    }

    @PostMapping("/{id}/logo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OrganizationRequest uploadLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        User caller = currentUser(authentication);
        assertOwnership(id, caller.getOrganization().getId());
        return orgServ.uploadLogo(id, file);
    }

    @PostMapping("/{id}/favicon")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OrganizationRequest uploadFavicon(@PathVariable Long id, @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        User caller = currentUser(authentication);
        assertOwnership(id, caller.getOrganization().getId());
        return orgServ.uploadFavicon(id, file);
    }

    // Sin @PreAuthorize a propósito: el navegador carga <img>/<link rel="icon"> sin mandar
    // el JWT, así que estos dos endpoints tienen que ser públicos (ver el permitAll específico
    // en SecurityConfig). No exponen nada sensible, solo la imagen de marca de la organización.
    @GetMapping("/{id}/logo")
    public ResponseEntity<byte[]> getLogo(@PathVariable Long id) {
        return orgServ.loadLogo(id)
                .map(file -> ResponseEntity.ok().contentType(MediaType.parseMediaType(file.contentType())).body(file.content()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/favicon")
    public ResponseEntity<byte[]> getFavicon(@PathVariable Long id) {
        return orgServ.loadFavicon(id)
                .map(file -> ResponseEntity.ok().contentType(MediaType.parseMediaType(file.contentType())).body(file.content()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
