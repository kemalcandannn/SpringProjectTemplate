package tr.bays.mapper.yalin.kullanici;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import tr.bays.dto.yalin.kullanici.KullaniciYalinDto;
import tr.bays.entity.kullanici.Kullanici;
import tr.bays.mapper.DtoMapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface KullaniciYalinMapper extends DtoMapper<KullaniciYalinDto, Kullanici> {
}
