package org.recap.service;

import org.junit.Assert;
import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

/**
 * Created by harikrishnanv on 20/7/17.
 */
public class RestHeaderServiceUT extends BaseTestCase{

    @Autowired
    RestHeaderService restHeaderService;

    @Test
    public void testRestHeaderService(){
        Map<String,String> apiKey;
        HttpHeaders httpHeaders = restHeaderService.getHttpHeaders();
        Assert.assertEquals(httpHeaders.getContentType(),MediaType.APPLICATION_JSON);
    }
}
