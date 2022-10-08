package fongff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 // 開啟Swagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	// 設定Swagger的Docket的bean範例
	@Bean
	public Docket docket() {
		
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())// 設定Swagger資訊
				.groupName("fongff").enable(true)
				/** 設定是否啟動swagger,預設為true */
				.select()
				/**
				 * apis():指定掃描的介面 RequestHandlerSelectors:設定要掃描介面的方式 basePackage:指定要掃描的包
				 * any:掃面全部 none:不掃描 withClassAnnotation:掃描類上的註解(引數是類上註解的class物件)
				 * withMethodAnnotation:掃描方法上的註解(引數是方法上的註解的class物件)
				 */
				.apis(RequestHandlerSelectors.basePackage("fongff.controller"))
				/**
				 * paths():過濾路徑 PathSelectors:設定過濾的路徑 any:過濾全部路徑 none:不過濾路徑
				 * ant:過濾指定路徑:按照按照Spring的AntPathMatcher提供的match方法進行匹配
				 * regex:過濾指定路徑:按照String的matches方法進行匹配
				 */
				.paths(PathSelectors.any()).build();
	}

	// 設定Swagger資訊
	private ApiInfo apiInfo() {
		return new ApiInfo("fongff", "fongff API", "1.2", "",
				new Contact("PaulLee", "", "leecheenbao@gmail.com"), // 作者資訊
				"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * swagger-ui.html路径映射，浏览器中使用/api-docs访问
	 *
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/api-docs", "/swagger-ui.html");
	}


}