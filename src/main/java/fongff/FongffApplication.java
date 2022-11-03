package fongff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "fongff")
@SpringBootApplication
public class FongffApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FongffApplication.class, args);
//        test
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FongffApplication.class);
    }
}
