package com.ssangyong.GreenMarket.config;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ssangyong.GreenMarket.service.LoginService;

import lombok.extern.java.Log;

@Log
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	LoginService loginService;
	@Autowired
	private DataSource dataSource;

    // 자동로그인 - tokenRepository 구현
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();   //Spring Security에서 제공하는 비밀번호 암호화 객체
	}
	
	@Override  //WebSecurity를 통해 HTTP 요청에 대한 웹 기반 보안을 구성
	public void configure(WebSecurity web) throws Exception {
		// 파일 기준은 resources/static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
		web.ignoring().antMatchers("/css/**", "/js/**", "/image/**", "/lib/**");
	}

	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config..........");
		// antMatchers url 패턴에 대한 접근허용
		// permitAll: 모든사용자가 접근가능하다는 의미
		// hasRole : 특정권한을 가진 사람만 접근가능하다는 의미
		http.authorizeRequests() // HttpServletRequest에 따라 접근(access)을 제한
			// 로그인 필요
			.antMatchers("/trade/**").authenticated()
			.antMatchers("/item/itemdetail").authenticated()
			.antMatchers("/community/boarddetail", "/community/myBoardlist", "/community/register").authenticated()
			
			// 모두 허용
			.antMatchers("/**","/fragments/**","/center/centerlist","/layout/login/**","/exerciseinfoboard/boardlist","/dietdiaryboard/boardlist","/healthboard/boardlist","/sharingboard/boardlist","/naversearch/**", "/upload/**", "/body/**").permitAll() //   누구나 접근 허용
		.and()
			.formLogin() // form 기반으로 인증을 하도록 한다. 로그인 정보는 기본적으로 HttpSession을 이용
			.loginPage("/?showsLoginForm=true") // 로그인 페이지 링크 .... post의 이름이 같다면 loginProcessingUrl생략 	
	        .loginProcessingUrl("/")
			.defaultSuccessUrl("/index") // 로그인 성공 후 리다이렉트 주소				
			.permitAll()
			.and()
			.logout() // 로그아웃에 관한 설정을 의미
			.logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))
			.logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 주소
			.invalidateHttpSession(true)  // 세션 지우기
		.and()
			.csrf().disable() //csrf(크로스사이트 위조요청에 대한 설정) 토큰 비활성화 (test시에는 disable권장) 
			.exceptionHandling().accessDeniedPage("/login/accessDenied");// 403 예외처리 핸들링   권한이 없는 대상이 접속을시도했을 때
		
		// 자동 로그인
		http.rememberMe()
				.userDetailsService(loginService)
				.tokenRepository(tokenRepository());

		// 에러 핸들링 (CookieTheftException 등)
		http.addFilterAfter(new ExceptionHandlerFilter(), SecurityContextHolderAwareRequestFilter.class);
	}
	
	@Override
	  public void configure(AuthenticationManagerBuilder auth) throws Exception { // 
	    auth.userDetailsService(loginService)
	    	// 해당 서비스(userService)에서는 UserDetailsService를 implements해서 
	        // loadUserByUsername() 구현해야함 (서비스 참고)
	    	.passwordEncoder(new BCryptPasswordEncoder()); 
	   }
	
}