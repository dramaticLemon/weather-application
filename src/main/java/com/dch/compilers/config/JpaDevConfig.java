package com.dch.compilers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("DEV")
@PropertySource("classpath:database-dev.properties")
public class JpaDevConfig extends BaseJpaConfig{
	
}
