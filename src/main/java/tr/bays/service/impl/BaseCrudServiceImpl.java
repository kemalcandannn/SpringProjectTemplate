package tr.bays.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.bays.common.base.BaseDto;
import tr.bays.dao.BaseRepository;
import tr.bays.entity.BaseEntity;
import tr.bays.mapper.DtoMapper;
import tr.bays.service.BaseCrudService;

@SuppressWarnings("serial")
@Transactional
@Service
public abstract class BaseCrudServiceImpl<DTO extends BaseDto, ENTITY extends BaseEntity> implements Serializable, BaseCrudService<DTO> {
	protected static Log LOGGER = LogFactory.getLog(BaseCrudServiceImpl.class);
	protected BaseRepository<ENTITY> repo;
	protected DtoMapper<DTO, ENTITY> dtoMapper;

	public BaseCrudServiceImpl(BaseRepository<ENTITY> repo, DtoMapper<DTO, ENTITY> dtoMapper) {
		super();
		this.repo = repo;
		this.dtoMapper = dtoMapper;
	}

	@Transactional(readOnly = true)
	public List<DTO> listele() {
		return dtoMapper.entityListToDtoList(repo.findAll());
	}

	@Transactional(readOnly = true)
	public DTO getir(long id) {
		return dtoMapper.entityToDto(repo.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " BULUNAMADI")));
	}

	@Transactional
	public DTO kaydet(DTO dto) {
		ENTITY entity = dtoMapper.dtoToEntity(dto);

		entity = repo.save(entity);
		dto = dtoMapper.entityToDto(entity);
		return dto;
	}

	@Transactional
	public List<DTO> kaydet(List<DTO> dtos) {
		List<ENTITY> entities = dtoMapper.dtoListToEntityList(dtos);

		entities = repo.saveAll(entities);
		dtos = dtoMapper.entityListToDtoList(entities);
		return dtos;
	}

	@Transactional
	public void sil(Long id) {
		repo.deleteById(id);
	}

	@Transactional
	public void sil(DTO dto) {
		ENTITY entity = dtoMapper.dtoToEntity(dto);
		repo.delete(entity);
	}

	@Transactional
	public void sil(List<DTO> dtos) {
		List<ENTITY> entities = dtoMapper.dtoListToEntityList(dtos);
		repo.deleteAll(entities);
	}

}
