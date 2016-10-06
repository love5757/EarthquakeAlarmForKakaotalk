package com.yerina.earthquake.service.impl;

import com.google.gson.Gson;
import com.yerina.earthquake.domain.RegionResult;
import com.yerina.earthquake.domain.request.RegionReq;
import com.yerina.earthquake.service.inf.SearchShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philip on 2016-10-06.
 */
@Service
public class SearchShelterServiceImpl implements SearchShelterService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public RegionResult searchRegion(RegionReq orgCd) {

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String url = "http://www.safekorea.go.kr/idsiSFK/sfk/ca/cac/are2/area2List.do";

        final ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, orgCd, String.class);
        RegionResult retult = new Gson().fromJson(stringResponseEntity.getBody(), RegionResult.class);

        return retult;
    }
}
