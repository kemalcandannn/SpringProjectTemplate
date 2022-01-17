package tr.bays.customdao.yetki;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

	protected static Logger LOGGER = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String refererUrl = request.getHeader("Referer");

		LOGGER.info(">>> CIKIS YAPILDIIIII - " + refererUrl);

		if (request.getParameter("yetkisizError") != null) {
			response.sendRedirect(request.getContextPath() + "/login?yetkisizError=true");
		} else {
			super.onLogoutSuccess(request, response, authentication);
		}
		
	}
}