package tr.bays;

import java.util.Collections;
import java.util.Locale;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ocpsoft.pretty.PrettyFilter;
import com.sun.faces.config.ConfigureListener;

import tr.bays.common.view.BaysViewScoped;
import tr.bays.common.view.ViewScope;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@ServletComponentScan
public class BaysApplication {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("TR"));
		System.setProperty("user.language","TR");

		SpringApplication.run(BaysApplication.class, args);
	}

	// JSF Configration Başlangıc
	@Bean
	public ServletRegistrationBean<FacesServlet> facesServletRegistraiton() {
		ServletRegistrationBean<FacesServlet> registration = new ServletRegistrationBean<FacesServlet>(
				new FacesServlet(), new String[] { "*.xhtml" });
		registration.setName("Faces Servlet");
		registration.setLoadOnStartup(1);

		return registration;
	}

	@Configuration
	@Profile("dev")
	static class ConfigureJSFContextParameters implements ServletContextInitializer {

		@Override
		public void onStartup(ServletContext servletContext)
				throws ServletException {

			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.THEME", "apollo-#{guestPreferences.theme}");
			servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/primefaces-apollo.taglib.xml");
//			servletContext.setInitParameter("primefaces.THEME", "california-cyan");
//			servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/primefaces-california.taglib.xml");
			servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
			// Xhtml sayfalarında commentlerin parse edilmemesi.
			servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
			// primefaces icon set için
			servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
			
			//JSF ortamının sayfalardaki hata ayıklama bilgilerini yazdırmasını sağlar.
			servletContext.setInitParameter("primefaces.PROJECT_STAGE", "Development");
			
			//Javax.faces.STATE_SAVING_METHOD parametresini "server" (varsayılan değer) olarak ayarlamak, bu parametreyi "client" olarak 
			//ayarlamaktan daha iyi performans sağlar. Bunun nedeni, sunucu durumunun kaydedilmesi durumun serileştirilmesini gerektirmemesidir.
			servletContext.setInitParameter("primefaces.STATE_SAVING_METHOD", "server");
			
			//Facelets derleyicisinin sayfalardaki değişiklikleri kontrol edene kadar beklemesi gereken süreyi belirtir.
			servletContext.setInitParameter("primefaces.FACELETS_REFRESH_PERIOD", "1");
			
			servletContext.setInitParameter("primefaces.FACELETS_BUFFER_SIZE", "500000");
			servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
			
			servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", Boolean.TRUE.toString());
			
			servletContext.setInitParameter("primefaces.PRIVATE_CAPTCHA_KEY", "6LcwYNYZAAAAALbUzVSxGQcemS9HGDtDoIr_9N4D");
			servletContext.setInitParameter("primefaces.PUBLIC_CAPTCHA_KEY", "6LcwYNYZAAAAAEC6Ji_ooZP9Gr6pqlUeK4mF3Tnn");
			
			servletContext.setInitParameter("primefaces.CSP", "true");
			servletContext.setInitParameter("primefaces.CSP_POLICY", "script-src 'self' https: *.googleapis.com");
			
			//primefaces excention
			servletContext.setInitParameter("primefaces.extendions.DELIVER_UNCOMPRESSED_RESOURCES", Boolean.FALSE.toString());
		}
	}
	
	@Configuration
	@Profile("production")
	static class ConfigureJSFContextParametersProd implements ServletContextInitializer {

		@Override
		public void onStartup(ServletContext servletContext)
				throws ServletException {

			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.THEME", "apollo-#{guestPreferences.theme}");
			servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/primefaces-apollo.taglib.xml");
			servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
			// Xhtml sayfalarında commentlerin parse edilmemesi.
			servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
			// primefaces icon set için
			servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
			
			//JSF ortamının sayfalardaki hata ayıklama bilgilerini yazdırmasını sağlar.
			servletContext.setInitParameter("primefaces.PROJECT_STAGE", "Production");
			
			//Javax.faces.STATE_SAVING_METHOD parametresini "server" (varsayılan değer) olarak ayarlamak, bu parametreyi "client" olarak 
			//ayarlamaktan daha iyi performans sağlar. Bunun nedeni, sunucu durumunun kaydedilmesi durumun serileştirilmesini gerektirmemesidir.
			servletContext.setInitParameter("primefaces.STATE_SAVING_METHOD", "server");
			
			//Facelets derleyicisinin sayfalardaki değişiklikleri kontrol edene kadar beklemesi gereken süreyi belirtir.
			servletContext.setInitParameter("primefaces.FACELETS_REFRESH_PERIOD", "1");
			
			servletContext.setInitParameter("primefaces.FACELETS_BUFFER_SIZE", "500000");
			servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
			
			servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", Boolean.TRUE.toString());
			
			servletContext.setInitParameter("primefaces.PRIVATE_CAPTCHA_KEY", "6LcwYNYZAAAAALbUzVSxGQcemS9HGDtDoIr_9N4D");
			servletContext.setInitParameter("primefaces.PUBLIC_CAPTCHA_KEY", "6LcwYNYZAAAAAEC6Ji_ooZP9Gr6pqlUeK4mF3Tnn");
			
			servletContext.setInitParameter("primefaces.CSP", "true");
			servletContext.setInitParameter("primefaces.CSP_POLICY", "script-src 'self' https: *.googleapis.com");
			
			//primefaces excention
			servletContext.setInitParameter("primefaces.extendions.DELIVER_UNCOMPRESSED_RESOURCES", Boolean.FALSE.toString());
		}
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}

	@Bean
	public CustomScopeConfigurer viewScopeConfigurer() {
		CustomScopeConfigurer bean = new CustomScopeConfigurer();
		bean.setScopes(Collections.singletonMap(BaysViewScoped.NAME, ViewScope.class));
		return bean;
	}
	// JSF Configration Sonu
	
	@Bean
	public FilterRegistrationBean<PrettyFilter> prettyFilter() {
	    FilterRegistrationBean<PrettyFilter> prettyFilter = new FilterRegistrationBean<PrettyFilter>(new PrettyFilter());
	    prettyFilter.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR);
	    prettyFilter.addUrlPatterns("/*");
	    
	    return prettyFilter;
	}
	
}
