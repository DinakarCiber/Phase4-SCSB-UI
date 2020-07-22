package org.recap.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Http Request interceptor, which logs the request details like URI, Request Method, Header
 * and Body content
 *
 * @author  Ramanujam (CEI)
 */

@Slf4j
@Component
public class HttpInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        log.info("Request Address: {}", request.getURI());
        log.info("Request method: {}", request.getMethod());
        log.info("Request content: {}", new String(body));
        log.info("request header: {}", request.getHeaders());
        return execution.execute(request, body);
    }
}