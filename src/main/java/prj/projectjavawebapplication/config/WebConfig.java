package prj.projectjavawebapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import prj.projectjavawebapplication.security.AuthInterceptor;
import prj.projectjavawebapplication.security.RoleInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor auth;
    private final RoleInterceptor role;

    public WebConfig(AuthInterceptor auth, RoleInterceptor role) {
        this.auth = auth;
        this.role = role;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(auth)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/register",
                        "/logout",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                );

        registry.addInterceptor(role)
                .addPathPatterns("/**");
    }
}
