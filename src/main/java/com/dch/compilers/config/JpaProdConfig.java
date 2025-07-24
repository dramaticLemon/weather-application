package com.dch.compilers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("PROD")
@PropertySource("classpath:database-prod.properties")
public class JpaProdConfig extends BaseJpaConfig{
	
}