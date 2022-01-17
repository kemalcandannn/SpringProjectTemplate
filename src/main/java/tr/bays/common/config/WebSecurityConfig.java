package tr.bays.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import tr.bays.customdao.yetki.CustomAuthenticationFailureHandler;
import tr.bays.customdao.yetki.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableScheduling
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	protected static Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);
	
//	@Autowired
//	private UserDetailsService userDetailsService;
	 
	@Autowired
	private CustomIpAuthenticationProvider authenticationProvider;
	 
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>> WebSecurityConfig.configure(AuthenticationManagerBuilder auth) icindeyiz");
//    	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    	auth.authenticationProvider(authenticationProvider);
//        auth.authenticationProvider(authenticationProvider).jdbcAuthentication().passwordEncoder(passwordEncoder());
    	LOGGER.info("------------------------------------3333");
//    	try {
//			auth.authenticationProvider(authenticationProvider);
//		} catch (BadCredentialsException e) {
//			LOGGER.info("##################################################################"+e.getMessage());
//			e.printStackTrace();
//		}
//        try {
//			auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>> WebSecurityConfig.configure(HttpSecurity http) icindeyiz");

        SecurityContextHolder.clearContext();

        http.csrf().disable().headers().disable();
        //http.headers().disable();

        http.authorizeRequests()
        		.antMatchers("/login").permitAll()
        		.antMatchers("/captcha-servlet").permitAll()
        		.antMatchers("/parolamiUnuttum.xhtml").permitAll()
        		.antMatchers("/yetkisiz").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")//.permitAll()
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .defaultSuccessUrl("/index.xhtml", true)
                .failureHandler(authenticationFailureHandler())
//                .failureHandler(authenticationFailureHandler("/login?error=true"))
//                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(logoutSuccessHandler())
//                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        
        LOGGER.info("------------------------------------0000");

        http.sessionManagement().maximumSessions(1).and()
                .invalidSessionUrl("/")
                .sessionFixation().newSession();
        
        LOGGER.info("------------------------------------0001");
        
//        http
//        .requiresChannel()
//        .anyRequest()
//        .requiresSecure();
        LOGGER.info("------------------------------------0002");
        http.headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"));
//        http.headers().frameOptions().sameOrigin();	//sdf ekrani icin
        
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
    	LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>> WebSecurityConfig.configure(WebSecurity webSecurity) icindeyiz");
    	LOGGER.info("------------------------------------1111");
//        webSecurity.ignoring().antMatchers("/javax.faces.resource/**").antMatchers("/resources/california-layout/**");
        webSecurity.ignoring().antMatchers("/javax.faces.resource/**").antMatchers("/resources/public/**").antMatchers("/services/**").antMatchers("/applet/**");
    }
    
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
    	LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>> WebSecurityConfig.customAuthenticationManager icindeyiz");
    	LOGGER.info("------------------------------------2222");
        return authenticationManager();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
    
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
    	return new CustomAuthenticationFailureHandler();
    }
    
//    @Bean
//    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
//    	LOGGER.info(">> AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//    	SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
//    	failureHandler.setUseForward(true);
//    	failureHandler.setDefaultFailureUrl("/login?error=true");
//    	return failureHandler;
//    }

}
