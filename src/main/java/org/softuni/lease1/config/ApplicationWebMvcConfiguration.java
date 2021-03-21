package org.softuni.lease1.config;

import org.softuni.lease1.web.interceptors.TasksInterceptor;
import org.softuni.lease1.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {
    private final TitleInterceptor titleInterceptor;
    private final TasksInterceptor tasksInterceptor;

    @Autowired
    public ApplicationWebMvcConfiguration(TitleInterceptor titleInterceptor, TasksInterceptor tasksInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.tasksInterceptor = tasksInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.tasksInterceptor);
    }
}
