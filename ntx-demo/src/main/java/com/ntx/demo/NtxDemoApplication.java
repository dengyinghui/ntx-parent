package com.ntx.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan(value = {"com.ntx"})
public class NtxDemoApplication {

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

}
