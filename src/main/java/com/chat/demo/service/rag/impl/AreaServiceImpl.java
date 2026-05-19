package com.chat.demo.service.rag.impl;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chat.demo.model.Area;
import com.chat.demo.repository.AreaRepository;
import com.chat.demo.service.rag.AreaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService{
	
	private final AreaRepository areaRepository;

	@Override
	public Area save(Area area) {
		return areaRepository.save(area);
	}

	@Override
	public Area update(Long id, Area area) {
		   Area existing = areaRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Area not found"));

	        existing.setName(area.getName());
	        existing.setDescription(area.getDescription());
	        existing.setOrganization(area.getOrganization());

	        return areaRepository.save(existing);
	}

	@Override
	public Optional<Area> findById(Long id) {
	          return areaRepository.findById(id);
	}

	@Override
	public Optional<Area> findByName(String name) {
		   return areaRepository.findByName(name);
	}

	@Override
	public List<Area> findByOrganization(Long organizationId) {
		return areaRepository.findByOrganizationId(organizationId);
	}

	@Override
	public void delete(Long id) {
		  areaRepository.deleteById(id);
		
	}
	
	/**
	 * Qué hace esto en el chatbot

Ejemplo real.

crear áreas
RRHH
Contabilidad
Salud
Cultura
listar áreas por organización

Cuando usuario inicia chat:

Seleccioná un área:

backend:

areaService.findByOrganization(orgId);

y devuelve menú.

Ejemplo frontend:

1. RRHH
2. Salud
3. Cultura

Esto conecta muy bien con la idea de chatbot guiado
	 */

}
