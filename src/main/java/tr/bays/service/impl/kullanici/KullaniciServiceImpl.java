package tr.bays.service.impl.kullanici;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.bays.common.QueryResult;
import tr.bays.customdao.kullanici.CustomKullaniciRepository;
import tr.bays.dao.BaseRepository;
import tr.bays.dto.kullanici.KullaniciDto;
import tr.bays.entity.kullanici.Kullanici;
import tr.bays.mapper.DtoMapper;
import tr.bays.service.impl.BaseCrudServiceImpl;
import tr.bays.service.kullanici.KullaniciService;
import util.enums.GerekliEnum;

@SuppressWarnings("serial")
@Service
public class KullaniciServiceImpl extends BaseCrudServiceImpl<KullaniciDto, Kullanici> implements KullaniciService {

	private CustomKullaniciRepository customKullaniciRepository;

	public KullaniciServiceImpl(BaseRepository<Kullanici> repo, DtoMapper<KullaniciDto, Kullanici> dtoMapper, CustomKullaniciRepository customKullaniciRepository) {
		super(repo, dtoMapper);

		this.customKullaniciRepository = customKullaniciRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public QueryResult<KullaniciDto> listele(int firstRecord, int pageSize, KullaniciDto sorguKriteri, Map<String, SortMeta> sort) {
		int pageNumber = firstRecord / pageSize;

		Page<Kullanici> page = listeleInternal(pageSize, pageNumber, sorguKriteri, sort);
		return new QueryResult<KullaniciDto>(dtoMapper.entityListToDtoList(page.getContent()), (int) page.getTotalElements());
	}

	private Page<Kullanici> listeleInternal(int pageSize, int pageNumber, KullaniciDto sorguKriteri, Map<String, SortMeta> sort) {
		return customKullaniciRepository.sorgula(pageSize, pageNumber, dtoMapper.dtoToEntity(sorguKriteri), sort);
	}

	@Override
	public List<KullaniciDto> combo(String query) {
		return dtoMapper.entityListToDtoList(customKullaniciRepository.combo(query));
	}

	@Override
	public KullaniciDto kullaniciAdiIleGetir(String kullaniciAdi) {
		return dtoMapper.entityToDto(customKullaniciRepository.kullaniciAdiIleGetir(kullaniciAdi));
	}

	@Override
	@Transactional
	public KullaniciDto parolaGuncelle(KullaniciDto kullaniciDto, String parola) {
		kullaniciDto.setParola(parola);
		kullaniciDto.setParola_degistir(GerekliEnum.GEREKSIZ.getValue());
		kullaniciDto.setParola_degistirme_tarihi(new Date());
		kullaniciDto = kaydet(kullaniciDto);
		
		return kullaniciDto;
	}

}
