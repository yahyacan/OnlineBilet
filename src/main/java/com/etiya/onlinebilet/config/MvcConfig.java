package com.etiya.onlinebilet.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.etiya.onlinebilet")
public class MvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry
////                .addResourceHandler("/resources/**")
////                .addResourceLocations("/resources/");
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/META-INF/resources/static/js");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/META-INF/resources/static/css");
//    }

//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//
//        bean.setViewClass(JstlView.class);
//        bean.setPrefix("/WEB-INF/templates/");
//        bean.setSuffix(".html");
//
//        return bean;
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/havalimani").setViewName("havalimani");
        registry.addViewController("/havayolu").setViewName("havayolu");
        registry.addViewController("/rezervasyon").setViewName("rezervasyon");
        registry.addViewController("/rota").setViewName("rota");
        registry.addViewController("/ucus").setViewName("ucus");
        registry.addViewController("/ucusArama").setViewName("ucusArama");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/**").addResourceLocations("/");
    }


}