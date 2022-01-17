package tr.bays.mapper;

import java.util.List;

public interface DtoMapper<DTO, ENTITY> {

	ENTITY dtoToEntity(DTO dto);

	DTO entityToDto(ENTITY entity);

	List<DTO> entityListToDtoList(List<ENTITY> entityList);

	List<ENTITY> dtoListToEntityList(List<DTO> entityList);

}
