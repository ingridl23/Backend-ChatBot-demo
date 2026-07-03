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
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.AreaRequest;
import com.chat.demo.dto.AreaResponse;
import com.chat.demo.mapper.AreaMapper;
import com.chat.demo.model.Area;
import com.chat.demo.model.User;
import com.chat.demo.service.auth.CustomUserDetails;
import com.chat.demo.service.rag.AreaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaServ;
    private final AreaMapper areaMapper;

    private User currentUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getOrganization() == null) {
            throw new RuntimeException("Authenticated user has no organization assigned");
        }
        return user;
    }

    private Area findOwnedOrThrow(Long id, Long callerOrganizationId) {
        Area area = areaServ.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found"));
        if (!area.getOrganization().getId().equals(callerOrganizationId)) {
            throw new AccessDeniedException("Area does not belong to your organization");
        }
        return area;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AreaResponse createArea(@RequestBody AreaRequest request, Authentication authentication) {
        User caller = currentUser(authentication);
        Area area = Area.builder()
                .name(request.getName())
                .description(request.getDescription())
                .organization(caller.getOrganization())
                .build();
        return areaMapper.toResponse(areaServ.save(area));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AreaResponse updateArea(@PathVariable Long id, @RequestBody AreaRequest request,
            Authentication authentication) {
        User caller = currentUser(authentication);
        findOwnedOrThrow(id, caller.getOrganization().getId());

        Area area = Area.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return areaMapper.toResponse(areaServ.update(id, area));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AREA_ADMIN','USER')")
    public List<AreaResponse> getAreas(Authentication authentication) {
        User caller = currentUser(authentication);
        return areaServ.findByOrganization(caller.getOrganization().getId())
                .stream()
                .map(areaMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AREA_ADMIN','USER')")
    public AreaResponse getAreaById(@PathVariable Long id, Authentication authentication) {
        User caller = currentUser(authentication);
        Area area = findOwnedOrThrow(id, caller.getOrganization().getId());
        return areaMapper.toResponse(area);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteArea(@PathVariable Long id, Authentication authentication) {
        User caller = currentUser(authentication);
        findOwnedOrThrow(id, caller.getOrganization().getId());
        areaServ.delete(id);
    }
}
