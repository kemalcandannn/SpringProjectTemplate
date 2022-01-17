package tr.bays.mapper.yalin.kullanici;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import tr.bays.dto.yalin.kullanici.KullaniciIletisimYalinDto;
import tr.bays.entity.kullanici.KullaniciIletisim;
import tr.bays.mapper.DtoMapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface KullaniciIletisimYalinMapper extends DtoMapper<KullaniciIletisimYalinDto, KullaniciIletisim> {
}
