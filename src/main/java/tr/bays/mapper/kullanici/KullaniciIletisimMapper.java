package tr.bays.mapper.kullanici;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import tr.bays.dto.kullanici.KullaniciIletisimDto;
import tr.bays.entity.kullanici.KullaniciIletisim;
import tr.bays.mapper.DtoMapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface KullaniciIletisimMapper extends DtoMapper<KullaniciIletisimDto, KullaniciIletisim> {
}
