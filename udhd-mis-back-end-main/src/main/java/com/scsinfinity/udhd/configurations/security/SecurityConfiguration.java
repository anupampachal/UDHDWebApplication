package com.scsinfinity.udhd.configurations.security;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.security.CustomMobileAuthenticationProvider;
import com.scsinfinity.udhd.services.security.CustomUsernameAuthenticationProvider;
import com.scsinfinity.udhd.services.security.JWTRequestFilter;

import lombok.AllArgsConstructor;

@Configuration
@Import(SecurityProblemSupport.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final ApplicationEventPublisher applicationEventPublisher;
	private final CustomUsernameAuthenticationProvider customUsernameAuthenticationProvider;
	private final CustomMobileAuthenticationProvider customMobileAuthenticationProvider;
	private final CorsFilter corsFilter;
	private final JWTRequestFilter jwtRequestFilter;
	private static final String[] AUTH_WHITELIST = { "/authenticate", "/swagger-resources/**", "/swagger-ui/**",
			"/v3/api-docs", "/webjars/**" };
	private static final String[] iaAuditorExclusiveURLS = { "/api/audit/ia/create-step1-report/**" };

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationEventPublisher))
				.authenticationProvider(customUsernameAuthenticationProvider)
				.authenticationProvider(customMobileAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**").antMatchers("/resources/static/**").antMatchers("/*.{js,html,css}")
				.antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html,css}").antMatchers("/i18n/**")
				.antMatchers("/content/**").antMatchers("/swagger-ui/index.html").antMatchers("/test/**")
				.antMatchers("/h2-console/**")
				// .antMatchers("/resources/**").antMatchers("/resources/static/**")
				.antMatchers("/").antMatchers("/index.html");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @// @formatter:off

		http.csrf().disable()

				.cors()
				 .and()
				.addFilterBefore(corsFilter, CsrfFilter.class).exceptionHandling()
				//.authenticationEntryPoint(authEntryProblemSupport).accessDeniedHandler(accessDeniedProblemSupport).and()
				//.rememberMe().rememberMeServices(rememberMeServices()).rememberMeParameter("rememberMe")
				//.key(scsProperties.getSecurity().getRememberMe().getKey())
				
				//.and().formLogin().successHandler(ajaxAuthenticationSuccessHandler())
				//.failureHandler(ajaxAuthenticationFailureHandler())
				.and().logout().logoutUrl("/api/logout")
				//.logoutSuccessUrl("/auth").deleteCookies("SESSION", "JSESSIONID").permitAll()
				.and().headers()
				.frameOptions().disable()
				.and().authorizeRequests()
				.antMatchers("/api/audit/agir/**",
						"/api/audit/cag/**"
						).hasAnyAuthority(
						AuthorityConstants.ROLE_FLIA ,AuthorityConstants.ROLE_ULB_ACCOUNTANT,
						AuthorityConstants.ROLE_INTERNAL_AUDITOR,AuthorityConstants.ROLE_ULB_CMO ,
						AuthorityConstants.ROLE_SLPMU_ADMIN ,AuthorityConstants.ROLE_SLPMU_AUDIT ,
						AuthorityConstants.ROLE_SLPMU_ACCOUNT, AuthorityConstants.ROLE_SLPMU_UC,
						AuthorityConstants.ROLE_UDHD_SO,AuthorityConstants.ROLE_UDHD_PSEC,
						AuthorityConstants.ROLE_UDHD_SEC)
				.antMatchers("/api/audit/agir/save",
						"/api/audit/agir/criteria").hasAnyAuthority(AuthorityConstants.ROLE_FLIA,AuthorityConstants.ROLE_ULB_ACCOUNTANT)
				.antMatchers("/api/settings/division/save/**",
						"/api/settings/district/save/**",
						"/api/settings/ulb/save/**",
						"/api/settings/user-group/post/save/**",
						"/api/settings/user-mgt/ulb/**"
						).hasAnyAuthority(AuthorityConstants.ROLE_SLPMU_IT, AuthorityConstants.ROLE_UDHD_IT)
				.antMatchers("/api/deas/trial-balance/file/**",
						"/api/deas/annual-finance/file/**",
						"/api/deas/historic-upload/file/**",
						"/api/deas/budget-upload/file/**",
						"/api/deas/fixed-assets/file/**",
						"/api/deas/property-tax/file/**",
						"/api/deas/fixed-assets/file/**").hasAnyAuthority(AuthorityConstants.ROLE_ULB_ACCOUNTANT,AuthorityConstants.ROLE_FLIA,AuthorityConstants.ROLE_ULB_CMO)
				/*.antMatchers("/api/deas/income-expense/state/**","/api/deas/cash-and-bank-balance/**").hasAnyAuthority(AuthorityConstants.ROLE_FLIA ,AuthorityConstants.ROLE_ULB_ACCOUNTANT,
						AuthorityConstants.ROLE_INTERNAL_AUDITOR,AuthorityConstants.ROLE_ULB_CMO ,
						AuthorityConstants.ROLE_SLPMU_ADMIN ,AuthorityConstants.ROLE_SLPMU_AUDIT ,
						AuthorityConstants.ROLE_UDHD_SO,AuthorityConstants.ROLE_UDHD_PSEC,
						AuthorityConstants.ROLE_UDHD_SEC)*/
				.antMatchers(iaAuditorExclusiveURLS)
				.hasAnyAuthority(AuthorityConstants.ROLE_INTERNAL_AUDITOR)
				.antMatchers("/api/audit/ia/**")
				.hasAnyAuthority(AuthorityConstants.ROLE_FLIA ,AuthorityConstants.ROLE_ULB_ACCOUNTANT,
					AuthorityConstants.ROLE_INTERNAL_AUDITOR,AuthorityConstants.ROLE_ULB_CMO ,
					AuthorityConstants.ROLE_SLPMU_ADMIN ,AuthorityConstants.ROLE_SLPMU_AUDIT ,
					AuthorityConstants.ROLE_UDHD_SO,AuthorityConstants.ROLE_UDHD_PSEC,
					AuthorityConstants.ROLE_UDHD_SEC)
				.antMatchers("/api/audit/ac-dc/**")
				.hasAnyAuthority(AuthorityConstants.ROLE_SLPMU_ADMIN,AuthorityConstants.ROLE_SLPMU_ACCOUNT,AuthorityConstants.ROLE_SLPMU_AUDIT,
						AuthorityConstants.ROLE_SLPMU_UC,AuthorityConstants.ROLE_UDHD_PSEC,AuthorityConstants.ROLE_UDHD_SEC,AuthorityConstants.ROLE_UDHD_SO,
						AuthorityConstants.ROLE_ULB_ACCOUNTANT,AuthorityConstants.ROLE_ULB_CMO)
				.antMatchers(
						).hasAuthority(AuthorityConstants.ROLE_SLPMU_ADMIN)
				.antMatchers(
						"/api/settings/user-profile/**",
						"/api/settings/division/**",
						"/api/settings/district/**",
						"/api/settings/ulb/**",
						"/api/settings/user-group/**",
						"/api/deas/trial-balance/**",
						"/api/deas/annual-finance/**",
						"/api/deas/historic-upload/**",
						"/api/deas/budget-upload/**",
						"/api/deas/fixed-assets/**",
						"/api/deas/property-tax/**"
						).authenticated()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.antMatchers("/api/deas/income-expense/**",
						"/api/deas/cash-and-bank-balance/**",
						"/api/deas/balance-sheet/**").permitAll()
				.antMatchers("/api/auth").permitAll()
				.anyRequest()
				.authenticated();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//.maximumSessions(2)
		//.maxSessionsPreventsLogin(true)
		//.sessionRegistry(sessionRegistry());
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.headers()
		.httpStrictTransportSecurity()
		.includeSubDomains(true)
		.maxAgeInSeconds(31536000);

		// @formatter:on
	}

}
