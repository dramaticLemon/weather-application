package com.dch.compilers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.dch.compilers.manager.RedisManager;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;



@Configuration
@PropertySource("classpath:application.properties")
public class RedisConfig {
	
	@Autowired
	private Environment env;


	@Bean(destroyMethod = "shutdown")
	public RedisClient redisClient() {
		String host = env.getProperty("redis.host");
		int port = Integer.parseInt(env.getProperty("redis.port"));
		return RedisClient.create("redis://" + host + ":" + port);
	}

	@Bean(destroyMethod = "close")
    public StatefulRedisConnection<String, String> redisConnection(RedisClient redisClient) {
        return redisClient.connect();
    }

	@Bean
    public RedisCommands<String, String> redisCommands(StatefulRedisConnection<String, String> connection) {
        return connection.sync();
    }

	@Bean
    public RedisManager redisManager(RedisCommands<String, String> redisCommands) {
        return new RedisManager(redisCommands);
    }

}
