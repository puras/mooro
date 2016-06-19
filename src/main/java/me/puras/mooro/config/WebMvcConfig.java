package me.puras.mooro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.puras.common.holder.ObjectMapperHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author puras <he@puras.me>
 * @since 16/3/2  下午12:32
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(resourceConverter());
        converters.add(jacksonConverter());
        converters.add(stringConverter());
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = ObjectMapperHolder.getInstance().getNewMapper();
        converter.setObjectMapper(mapper);
        return converter;
    }

    @Bean
    StringHttpMessageConverter stringConverter() {
        return new StringHttpMessageConverter();
    }

    @Bean
    ResourceHttpMessageConverter resourceConverter() {
        ResourceHttpMessageConverter converter = new ResourceHttpMessageConverter();
        return converter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(3600);
    }
}