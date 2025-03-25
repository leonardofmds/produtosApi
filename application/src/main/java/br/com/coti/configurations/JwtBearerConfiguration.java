package br.com.coti.configurations;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.coti.filters.JwtTokenFilter;

@Configuration
public class JwtBearerConfiguration {

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> jwtFilter() {

        //Registrando o filtro que irá validar os TOKENS
        FilterRegistrationBean<JwtTokenFilter> filter = new FilterRegistrationBean<JwtTokenFilter>();
        filter.setFilter(new JwtTokenFilter());

        //Aplicando o filtro em toda a API
        filter.addUrlPatterns("/api/*");

        return filter;
    }
}
