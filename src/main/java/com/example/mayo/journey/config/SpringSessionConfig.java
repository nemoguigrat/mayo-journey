package com.example.mayo.journey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableJdbcHttpSession
public class SpringSessionConfig extends AbstractHttpSessionApplicationInitializer {
}
