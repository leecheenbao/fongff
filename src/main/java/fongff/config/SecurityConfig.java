package fongff.config;

import fongff.handle.MyAccessDeniedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 若要自訂登入邏輯則要繼承 WebSecurityConfigurerAdapter
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 注意！規定！要建立密碼演算的實例
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 表單提交
		http.formLogin()
			// loginpage.html 表單 action 內容
			.loginProcessingUrl("/login")
			// 自定義登入頁面
			.loginPage("/dashboard/login")
			// 登入成功之後要造訪的頁面
			.successForwardUrl("/dashboard/module/index")  // welcome 頁面
			// 登入失敗後要造訪的頁面
			.failureForwardUrl("/dashboard/login");
		
		// 授權認證
		http.authorizeHttpRequests()
			// 不需要被認證的頁面：/loginpage
			.antMatchers("/dashboard/login","/api/**",/*前台取資料*/
					"/css/**", "/js/**", "/images/**","/static/**","/assets/**","/action/**",      /*後台*/
					"/auth/**",
					"/swagger-ui.html",
					"/v2/api-docs/**",
					"/swagger-resources/**",
					"/webjars/**",
					"/api-docs").permitAll()
			// 權限判斷
//			// 必須要有 admin 權限才可以訪問
//			.antMatchers("/dashboard/register").hasAuthority("ADMIN")
//			// 其他指定任意角色都可以訪問
//			.antMatchers("/dashboard/module/**").hasAnyRole("USER", "ADMIN")
			// 其他的都要被認證
			.anyRequest().authenticated();
		
		// http.csrf().disable(); // 關閉 csrf 防護
		
		// 登出
		http.logout()
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/dashboard/login")
			.logoutRequestMatcher(new AntPathRequestMatcher("/dashboard/logout")); // 可以使用任何的 HTTP 方法登出
	
		// 異常處理
		http.exceptionHandling()
			//.accessDeniedPage("/異常處理頁面");  // 請自行撰寫
			.accessDeniedHandler(myAccessDeniedHandler);
		
		// 勿忘我（remember-me）
		http.rememberMe()
			.userDetailsService(userDetailsService)
			.tokenValiditySeconds(60); // 通常都會大於 session timeout 的時間
		
	}

}
