package tr.bays.mapper.kullanici;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import tr.bays.dto.kullanici.KullaniciDto;
import tr.bays.entity.kullanici.Kullanici;
import tr.bays.mapper.DtoMapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface KullaniciMapper extends DtoMapper<KullaniciDto, Kullanici> {
}
