package com.lyami.v1.config.mdc;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lyami.v1.filter.Slf4jMDCFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MDCFilterConfig {
	private final Slf4jMDCFilter slf4jMDCFilter;

    @Bean
    public FilterRegistrationBean<Slf4jMDCFilter> servletRegistrationBean() {
        final FilterRegistrationBean<Slf4jMDCFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(slf4jMDCFilter);
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
