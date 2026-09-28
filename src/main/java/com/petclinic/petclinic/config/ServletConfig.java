package com.petclinic.petclinic.config;

import com.petclinic.petclinic.repository.InquiryRepository;
import com.petclinic.petclinic.servlet.InquirySummaryServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the plain-Servlet endpoints used alongside the Spring MVC
 * controllers, so both approaches share the same embedded Tomcat container.
 */
@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<InquirySummaryServlet> inquirySummaryServlet(InquiryRepository inquiryRepository) {
        ServletRegistrationBean<InquirySummaryServlet> bean = new ServletRegistrationBean<>(
                new InquirySummaryServlet(inquiryRepository), "/servlet/inquiry-summary/*");
        bean.setName("inquirySummaryServlet");
        return bean;
    }
}
