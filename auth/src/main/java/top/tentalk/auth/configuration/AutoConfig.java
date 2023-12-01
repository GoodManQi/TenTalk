package top.tentalk.auth.configuration;


import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.tentalk.auth.interceptor.JwtAuthInterceptor;


@ConditionalOnWebApplication
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"top.tentalk.auth.configuration", "top.tentalk.auth.interceptor", "top.tentalk.auth.utils"})
public class AutoConfig implements WebMvcConfigurer {

    @Resource
    private transient JwtAuthInterceptor jwtAuthInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor).order(2);
    }
}
