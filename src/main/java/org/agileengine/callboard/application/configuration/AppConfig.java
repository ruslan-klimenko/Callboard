package org.agileengine.callboard.application.configuration;

import org.agileengine.callboard.application.FileExtension;
import org.agileengine.callboard.application.Path;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.agileengine.callboard.application.ApplicationData;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = ApplicationData.APPLICATION_FULL_NAME)
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(Path.VIEWS);
        viewResolver.setSuffix(FileExtension.JSP);
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations(Path.RESOURCES_LOCATION);
    }

}
