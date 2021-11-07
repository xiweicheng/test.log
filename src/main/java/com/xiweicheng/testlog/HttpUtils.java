package com.xiweicheng.testlog;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class HttpUtils {

    public static String get(String url) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(Constants.TRACE_ID, MDC.get(Constants.TRACE_ID));
        URI uri = new URI(url);
        RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
        ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);

        if (exchange.getStatusCode().equals(HttpStatus.OK)) {
            log.info("send http request success");
        }

        return exchange.getBody();
    }

}