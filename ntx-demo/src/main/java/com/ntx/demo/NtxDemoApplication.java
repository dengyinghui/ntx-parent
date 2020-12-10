package com.ntx.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan(value = {"com.ntx"})
@EnableDiscoveryClient
public class NtxDemoApplication {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    public static void main(String[] args) {
        SpringApplication.run(NtxDemoApplication.class, args);
    }


    /**
     * 重新定义Spring Boot 序列化,采用fastjson方式序列化对象
     * @return HttpMessageConverters
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);


        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /**
         * SerializerFeature.PrettyFormat 格式化输出
         * SerializerFeature.WriteMapNullValue 输出值为null的字段
         * SerializerFeature.DisableCircularReferenceDetect 消除循环引用
         * SerializerFeature.WriteNullStringAsEmpty 将为null的字段值显示为""
         * SerializerFeature.WriteNullListAsEmpty List字段如果为null,输出为[],而非null
         */
        SerializerFeature[] array = {SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullNumberAsZero};
        fastJsonConfig.setSerializerFeatures(array);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastJsonHttpMessageConverter);
    }

    /**
     * 重新定义redis的序列化
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setEnableDefaultSerializer(true);
        //定义key的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        //定义value的序列化方式
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return template;
    }

}
