package tr.bays.common.config;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;
import tr.bays.controller.sistem.SessionController;

@Slf4j
public class CustomFilter extends GenericFilterBean {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private SessionController sessionController;
	private HashMap<String, String> urlIslem = null;

	public CustomFilter() {
	}

	// urller ile islem id ler eslestiriliyor, 1 kez calistirilir
	private void init() {
		urlIslem = new HashMap<String, String>();
		urlIslem.put("/ilce", "ilce_listesi_sayfasi");
		urlIslem.put("/kullanici", "kullanici_listesi_sayfasi");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// ip cekme
		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
			if (urlIslem == null)
				init();
			
			String userIp = InetAddress.getLoopbackAddress().getHostAddress();
			String path = ((HttpServletRequest) request).getRequestURI();

			// bu sayfalar otomatik calistirilabilir
			if (
				path.equals("/yetkisiz") || 
				path.equals("/tanimsiz") || 
				path.equals("/index") || 
				path.equals("/parolaDegistir") || 
				path.contains("/tmpDosya/") || 
				path.startsWith("/js/") || 
				path.startsWith("/css/") || 
				path.startsWith("/img/") || 
				path.startsWith("/pdfs/")
			) {

				chain.doFilter(request, response);
				return;
			}

			if (sessionController == null) {// filter icinde autowired kullanilamiyor
				ServletContext servletContext = request.getServletContext();
				WebApplicationContext webApplicationContext = WebApplicationContextUtils
						.getWebApplicationContext(servletContext);
				sessionController = webApplicationContext.getBean(SessionController.class);
			}

			log.info(path);

			// bu sayfalar otomatik calistirilabilir
			if (path.equals("/index.xhtml")) {
				chain.doFilter(request, response);
				return;
			}

			// o pathe izni yoksa
			if (urlIslem.containsKey(path)) {
				if (sessionController.yetkiliMi(urlIslem.get(path)) == false) {
					log.info(urlIslem.get(path));
					log.info(userIp);
					redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response,
							"/logout?yetkisizError=true");
				}
			} else {
				log.info(path);
				if (sessionController == null)
					redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response,
							"/tanimsiz");
			}
			chain.doFilter(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}