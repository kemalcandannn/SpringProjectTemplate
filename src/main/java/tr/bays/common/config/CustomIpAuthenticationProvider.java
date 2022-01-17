package tr.bays.common.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import tr.bays.dto.kullanici.KullaniciDto;
import tr.bays.service.kullanici.KullaniciService;
import util.Util;

@Component
public class CustomIpAuthenticationProvider implements AuthenticationProvider {

	protected static Logger LOGGER = LoggerFactory.getLogger(CustomIpAuthenticationProvider.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HttpServletRequest request;

	private KullaniciService kullaniciService;

	@Value("${captcha_hatali_giris_sayisi}")
	private int captchaHataliGirisSayisi;

	public CustomIpAuthenticationProvider(KullaniciService kullaniciService) {
		super();
		this.kullaniciService = kullaniciService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
		String userIp = details.getRemoteAddress();

		LOGGER.info("----------------userIp:" + userIp);
		LOGGER.info("custom ip auth -> authenticate fonksiyonu içerisinde");

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());

		String responseCap2 = request.getParameter("j_bilgi");

		String sessionCap = (String) request.getSession().getAttribute("captcha");

		LOGGER.info("reCap2 = " + responseCap2);
		LOGGER.info("session reCap = " + sessionCap);

		if (Util.empty(username.trim()) || Util.empty(password.trim())) {
			throw new BadCredentialsException("alanlari doldurunuz");
		}

		if (request.getParameter("captcha") != null && responseCap2 != null && sessionCap != null
				&& !responseCap2.equals(sessionCap)) {
			if (!responseCap2.equals("key")) {
				throw new BadCredentialsException("Hatalı Captcha");
			}
		}

		LOGGER.info("url = " + request.getRequestURI());
		LOGGER.info("captcha remote = " + request.getParameter("captcha"));

		System.out.println(passwordEncoder.encode("candan"));

		KullaniciDto kullanici = kullaniciService.kullaniciAdiIleGetir(username);
		
		if (kullanici == null) {
			throw new BadCredentialsException("gecersiz kullanici");
		}
				
		if (!passwordEncoder.matches(password, kullanici.getParola())) {
			throw new BadCredentialsException("Parola Uyuşmazlığı");
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}