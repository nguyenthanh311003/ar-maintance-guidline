package com.capstone.ar_guideline.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
  @Value("${redis.host}")
  private String redisHost;

  @Value("${redis.port}")
  private int redisPort;

  @Value("${redis.username}")
  private String redisUsername;

  @Value("${redis.password}")
  private String redisPassword;

  @Value("${redis.usessl}")
  private boolean useSsl;

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
    config.setUsername(redisUsername);
    config.setPassword(redisPassword);

    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(config);
    lettuceConnectionFactory.setUseSsl(useSsl);

    return lettuceConnectionFactory;
  }

  @Bean
  @Primary
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new JdkSerializationRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new JdkSerializationRedisSerializer());
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}
