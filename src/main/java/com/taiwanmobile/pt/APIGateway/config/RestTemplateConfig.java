package com.taiwanmobile.pt.APIGateway.config;

import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory; 
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {
	
    @Bean
    public RestTemplate restTemplate() { 
        return new RestTemplate(httpRequestFactory());
//        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
//        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
//
//        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
//        HttpMessageConverter<?> converterTarget = null;
//        for (HttpMessageConverter<?> item : converterList) {
//            if (StringHttpMessageConverter.class == item.getClass()) {
//                converterTarget = item;
//                break;
//            }
//        }
//        if (null != converterTarget) {
//            converterList.remove(converterTarget);
//        }
//        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        
        ConnectionConfig connConfig = ConnectionConfig.custom()
        		.setSocketTimeout(5000, TimeUnit.MILLISECONDS)
        		.setConnectTimeout(3000, TimeUnit.MILLISECONDS)
        		.build();
        
        //设置整个连接池最大连接数 根据自己的场景决定
        connectionManager.setMaxTotal(200);
        //路由是对maxTotal的细分
        connectionManager.setDefaultMaxPerRoute(100);
        
        connectionManager.setDefaultConnectionConfig(connConfig);
        
//      RequestConfig requestConfig = RequestConfig.custom()
//              .setSocketTimeout(5000) //服务器返回数据(response)的时间，超过该时间抛出read timeout
//              .setConnectTimeout(3000)//连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
//              .setConnectionRequestTimeout(1000)//从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
//              .build();
        RequestConfig requestConfig = RequestConfig.custom()
        		.setConnectionRequestTimeout(1000, TimeUnit.MILLISECONDS)
        		.build();
        
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }
}