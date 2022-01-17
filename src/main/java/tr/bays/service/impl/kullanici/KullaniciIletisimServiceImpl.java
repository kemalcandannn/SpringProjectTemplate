package tr.bays.service.impl.kullanici;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.bays.common.QueryResult;
import tr.bays.customdao.kullanici.CustomKullaniciIletisimRepository;
import tr.bays.dao.BaseRepository;
import tr.bays.dto.kullanici.KullaniciIletisimDto;
import tr.bays.entity.kullanici.KullaniciIletisim;
import tr.bays.mapper.DtoMapper;
import tr.bays.service.impl.BaseCrudServiceImpl;
import tr.bays.service.kullanici.KullaniciIletisimService;
import util.enums.AktifEnum;

@SuppressWarnings("serial")
@Service
public class KullaniciIletisimServiceImpl extends BaseCrudServiceImpl<KullaniciIletisimDto, KullaniciIletisim> implements KullaniciIletisimService {

	private CustomKullaniciIletisimRepository customKullaniciIletisimRepository;

	public KullaniciIletisimServiceImpl(BaseRepository<KullaniciIletisim> repo, DtoMapper<KullaniciIletisimDto, KullaniciIletisim> dtoMapper, CustomKullaniciIletisimRepository customKullaniciIletisimRepository) {
		super(repo, dtoMapper);

		this.customKullaniciIletisimRepository = customKullaniciIletisimRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public QueryResult<KullaniciIletisimDto> listele(int firstRecord, int pageSize, KullaniciIletisimDto sorguKriteri, Map<String, SortMeta> sort) {
		int pageNumber = firstRecord / pageSize;

		Page<KullaniciIletisim> page = listeleInternal(pageSize, pageNumber, sorguKriteri, sort);
		return new QueryResult<KullaniciIletisimDto>(dtoMapper.entityListToDtoList(page.getContent()), (int) page.getTotalElements());
	}

	private Page<KullaniciIletisim> listeleInternal(int pageSize, int pageNumber, KullaniciIletisimDto sorguKriteri, Map<String, SortMeta> sort) {
		return customKullaniciIletisimRepository.sorgula(pageSize, pageNumber, dtoMapper.dtoToEntity(sorguKriteri), sort);
	}

	@Override
	public List<KullaniciIletisimDto> combo(String query) {
		return dtoMapper.entityListToDtoList(customKullaniciIletisimRepository.combo(query));
	}

	@Override
	public List<KullaniciIletisimDto> kullanicininTumIletisimBilgileriniGetir(Long kullanici_id) {
		List<KullaniciIletisim> asdf = customKullaniciIletisimRepository.kullanicininTumIletisimBilgileriniGetir(kullanici_id);
		return dtoMapper.entityListToDtoList(asdf);
	}

	@Override
	@Transactional
	public void aktifHaleGetir(KullaniciIletisimDto secilen) {
		List<KullaniciIletisimDto> kullaniciIletisimListesi = kullanicininTumIletisimBilgileriniGetir(secilen.getKullanici_id().getId());
		
		for (int i = 0; i < kullaniciIletisimListesi.size(); i++) {
			KullaniciIletisimDto kullaniciIletisimDto = kullaniciIletisimListesi.get(i);
			
			if(kullaniciIletisimDto.getId().equals(secilen.getId())) {
				kullaniciIletisimDto.setAktif(AktifEnum.AKTIF.getValue());
			}else {
				kullaniciIletisimDto.setAktif(AktifEnum.PASIF.getValue());
			}
		}
		
		kaydet(kullaniciIletisimListesi);
	}

}
