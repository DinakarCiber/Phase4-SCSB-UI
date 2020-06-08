package org.recap.security;

import org.recap.filter.CsrfCookieGeneratorFilter;
import org.recap.filter.ReCAPInstitutionFilter;
import org.recap.filter.ReCAPLogoutFilter;
import org.recap.service.CustomUserDetailsService;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by sheiks on 30/01/17.
 */
@Configuration
@EnableOAuth2Sso
public class MultiCASAndOAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Value("${default.cas.url.prefix}")
    private String casUrlPrefix;

    @Value("${default.cas.service.logout}")
    private String casServiceLogout;

    @Value("${app.service.logout.scsb}")
    private String appServiceLogout;

    @Autowired
    private CASPropertyProvider casPropertyProvider;

    @Autowired
    private UserAuthUtil userAuthUtil;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        OAuth2SsoProperties sso = getApplicationContext().getBean(OAuth2SsoProperties.class);

        LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(sso.getLoginPath());
        ReCAPExceptionTranslationFilter reCAPExceptionTranslationFilter = new ReCAPExceptionTranslationFilter(casPropertyProvider, loginUrlAuthenticationEntryPoint);
        http.addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
                .addFilterAfter(new ReCAPInstitutionFilter(), CsrfCookieGeneratorFilter.class)
                .addFilterAfter(reCAPExceptionTranslationFilter, ExceptionTranslationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint).and()
                .addFilter(casAuthenticationFilter())
                .addFilterBefore(reCAPLogoutFilter(), LogoutFilter.class)
                .addFilterBefore(requestCasGlobalLogoutFilter(), LogoutFilter.class);

        http.authorizeRequests().antMatchers("/","/home","/actuator","/actuator/prometheus").permitAll()
                .antMatchers("*").authenticated().anyRequest().authenticated();

        SessionManagementConfigurer<HttpSecurity> httpSecuritySessionManagementConfigurer = http.sessionManagement();
        httpSecuritySessionManagementConfigurer.invalidSessionUrl("/home");

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        // @formatter:on
    }


    /**
     * Register the CAS global logout filter.
     *
     * @return the LogoutFilter
     */
    @Bean
    public LogoutFilter requestCasGlobalLogoutFilter() {
        String logoutSuccessUrl = casServiceLogout + "?service=" + appServiceLogout;

        ReCAPSimpleUrlLogoutSuccessHandler reCAPSimpleUrlLogoutSuccessHandler = new ReCAPSimpleUrlLogoutSuccessHandler(userAuthUtil);
        reCAPSimpleUrlLogoutSuccessHandler.setDefaultTargetUrl(logoutSuccessUrl);

        LogoutFilter logoutFilter = new LogoutFilter(reCAPSimpleUrlLogoutSuccessHandler, new SecurityContextLogoutHandler());
        logoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"));


        return logoutFilter;
    }

    /**
     * Register the ReCAP logout filter.
     *
     * @return the ReCAPLogoutFilter
     */
    @Bean
    public ReCAPLogoutFilter reCAPLogoutFilter() {
        return new ReCAPLogoutFilter();
    }

    /**
     * Cas authentication filter cas authentication filter.
     *
     * @return the cas authentication filter
     * @throws Exception the exception
     */
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setFilterProcessesUrl("/j_spring_cas_security_check");
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return casAuthenticationFilter;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(casAuthenticationProvider());
    }


    /**
     * Register CAS authentication provider and set properties
     *
     * @return the CasAuthenticationProvider
     */
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(authenticationUserDetailsService());
        casAuthenticationProvider.setServiceProperties(casPropertyProvider.getServiceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("an_id_for_this_auth_provider_only");
        return casAuthenticationProvider;
    }

    /**
     * Register Authentication user details service .
     *
     * @return the AuthenticationUserDetailsService
     */
    @Bean
    public AuthenticationUserDetailsService authenticationUserDetailsService() {
        return new CustomUserDetailsService();
    }


    /**
     * Register ReCAPCas20ServiceTicketValidator.
     *
     * @return the ReCAPCas20ServiceTicketValidator
     */
    @Bean
    public ReCAPCas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new ReCAPCas20ServiceTicketValidator(casUrlPrefix);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/fonts/**").antMatchers("/images/**").antMatchers("/js/**")
                .antMatchers("/css/**").antMatchers("/lib/**");
    }

    /**
     * Register Http session event publisher for SCSB.
     *
     * @return the ReCAPHttpSessionEventPublisher
     */
    @Bean
    public ReCAPHttpSessionEventPublisher httpSessionEventPublisher() {
        return new ReCAPHttpSessionEventPublisher();
    }

}

