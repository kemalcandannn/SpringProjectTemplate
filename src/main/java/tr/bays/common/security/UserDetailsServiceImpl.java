package tr.bays.common.security;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.bays.dto.kullanici.KullaniciDto;
import tr.bays.service.kullanici.KullaniciService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	protected static Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private KullaniciService kullaniciService;

	public UserDetailsServiceImpl(KullaniciService kullaniciService) {
		super();
		this.kullaniciService = kullaniciService;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		KullaniciDto kullanici = kullaniciService.kullaniciAdiIleGetir(username);
		if (kullanici == null) {
			throw new UsernameNotFoundException(username);
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(kullanici.getKullanici_adi(), kullanici.getParola(), grantedAuthorities);

		return userDetails;
	}
}