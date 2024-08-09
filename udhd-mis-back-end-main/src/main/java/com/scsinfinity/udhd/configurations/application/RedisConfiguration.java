package com.scsinfinity.udhd.configurations.application;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.scsinfinity.udhd.services.message.MessageSubscriber;

/*@Configuration
@EnableCaching
@EnableRedisRepositories
@EnableConfigurationProperties(RedisProperties.class)*/
public class RedisConfiguration {

	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.port}")
	private int port;

	/*// @formatter:off
	@Value("${spring.redis.jedis.pool.max-total}")
	private int maxTotal;
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;
	
	@Bean
	public JedisClientConfiguration getJedisClientConfiguration() {
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder JedisPoolingClientConfigurationBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
				.builder();
		GenericObjectPoolConfig GenericObjectPoolConfig = new GenericObjectPoolConfig();
		GenericObjectPoolConfig.setMaxTotal(maxTotal);
		GenericObjectPoolConfig.setMaxIdle(maxIdle);
		GenericObjectPoolConfig.setMinIdle(minIdle);
		return JedisPoolingClientConfigurationBuilder.poolConfig(GenericObjectPoolConfig).build();
		// https://commons.apache.org/proper/commons-pool/apidocs/org/apache/commons/pool2/impl/GenericObjectPool.html
	}

	@Bean
	public JedisConnectionFactory getJedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		if (!StringUtils.hasText(password)) {
			redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
		}
		redisStandaloneConfiguration.setPort(port);
		return new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(getJedisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
//   	 redisTemplate.setKeySerializer(new StringRedisSerializer());
//   	 redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer()));
		return redisTemplate;
	}

	
	@Bean
	public LettuceConnectionFactory connectionFactory() {
	    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
	    connectionFactory.setHostName(redisHost);
	    connectionFactory.setPort(redisPort);
	    connectionFactory.setPassword(redisPassword);
	    return connectionFactory;
	}
	*/
	@Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        // Add some specific configuration here. Key serializers, etc.
        return template;
    }
	@Bean
	 
	  public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
	    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
	    		.defaultCacheConfig()
	    		.disableCachingNullValues()
	    		.entryTtl(Duration.ofMinutes(10))
	    		.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
	    		//.serializeValuesWith(new GenericJackson2JsonRedisSerializer()));
	    		//.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
	    		//.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(Jackson2JsonRedisSerializer.json()));
	    redisCacheConfiguration.usePrefix();

	    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
	      .cacheDefaults(redisCacheConfiguration).build();
	  }
	
	@Bean
	 @Primary
	public RedisSerializer<Object> getSerializer() {
		return new Jackson2JsonRedisSerializer<Object>(Object.class);
	}
	@Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MessageSubscriber());
    }
	
	 @Bean
	    RedisMessageListenerContainer redisContainer(LettuceConnectionFactory lettuceConnectionFactory) {
	        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	        container.setConnectionFactory(lettuceConnectionFactory);
	        container.addMessageListener(messageListener(), topic());
	        return container;
	    }
	/* @Bean
	    IMessagePublisher redisPublisher(LettuceConnectionFactory lettuceConnectionFactory) {
	        return new MessagePublisher(redisTemplate(lettuceConnectionFactory), topic());
	    }
	 */
	@Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }
	
	@Bean
    public RedisCustomConversions redisCustomConversions(OffsetDateTimeToBytesConverter offsetToBytes,
                                                         BytesToOffsetDateTimeConverter bytesToOffset) {
        return new RedisCustomConversions(Arrays.asList(offsetToBytes, bytesToOffset));
    }

}
