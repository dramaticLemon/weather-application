package com.dch.compilers.manager;

import io.lettuce.core.api.sync.RedisCommands;

public class RedisManager {
	private final RedisCommands<String, String> redis;
	public RedisManager(RedisCommands<String, String> redis) {
		this.redis = redis;	
	}

	public void set(String key, String value, long ttlSeconds) {
        redis.setex(key, ttlSeconds, value);
    }

    public String get(String key) {
        return redis.get(key);
    }
}
