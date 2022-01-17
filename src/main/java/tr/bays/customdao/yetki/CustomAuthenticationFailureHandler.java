package tr.bays.customdao.yetki;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Controller;

@Controller
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	protected static Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

	public CustomAuthenticationFailureHandler() {
		super();
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("timestamp", Calendar.getInstance().getTime());
		data.put("exception", exception.getMessage());
		LOGGER.info("exception = ");
		LOGGER.info(exception.getMessage());
		
		if (exception.getMessage().equals("Invalid IP Address")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?ipError=true");
		} else if (exception.getMessage().equals("5 giristen fazla")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?5giris=true");
		} else if (exception.getMessage().equals("yetkisiz islem")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?yetkisizError=true");
		} else if (exception.getMessage().equals("reCaptcha was not successfully validated")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?captchaError=true");
		} else if (exception.getMessage().equals("Response contains invalid characters")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?captchaError=true");
		} else if (exception.getMessage().equals("reCaptcha was not successfully validated")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?captchaError=true");
		} else if (exception.getMessage().equals("Registration unavailable at this time.  Please try again later.")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?captchaError=true");
		} else if (exception.getMessage().equals("hatali captcha")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?captchaError=true");
		} else if (exception.getMessage().equals("gecersiz kullanici captchasiz")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?gecersizKulCaptchasizError=true");
		} else if (exception.getMessage().equals("gecersiz kullanici")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?gecersizKulError=true");
		} else if (exception.getMessage().equals("alanlari doldurunuz")) {
			getRedirectStrategy().sendRedirect(request, response, "/login?bosAlanError=true");
		} else {
			getRedirectStrategy().sendRedirect(request, response, "/login?error=true");
		}
	
	}
	
}