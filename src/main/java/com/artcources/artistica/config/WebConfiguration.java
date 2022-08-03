package com.artcources.artistica.config;


import com.artcources.artistica.web.interceptors.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private LocaleChangeInterceptor localeChangeInterceptor;
    private final UserInterceptor userInterceptor;

    public WebConfiguration(UserInterceptor userInterceptor,LocaleChangeInterceptor localeChangeInterceptor) {
        this.userInterceptor = userInterceptor;
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.userInterceptor);
        registry.addInterceptor(localeChangeInterceptor);
    }
}
