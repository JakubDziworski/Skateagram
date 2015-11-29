package com.kuba.skateagramclient.managers;

import com.google.inject.Singleton;
import com.kuba.skateagramclient.managers.SharedPrefsManager;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import javax.inject.Inject;

/**
 * Created by kuba on 28.11.2015.
 */
@Singleton
public class RequestBuilder {
    private RestTemplate restTemplate;
    @Inject
    SharedPrefsManager sharedPrefsManager;

    @Inject
    public RequestBuilder() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    public ResponseEntity<?> postToUrlWithoutCredentials(String url,Object object,Class<?> type) {
        try {
            return restTemplate.postForEntity(url,object,type);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>( e.getResponseBodyAsString(),e.getStatusCode());
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<?> getFromUrlWithoutCredentials(String url,Object object,Class<?> type) {
        try {
            return restTemplate.getForEntity(url,type);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<?> postToUrl(String url,Object object,Class<?> type) {
        HttpEntity<?> httpEntity = getAuthorizationEntity();
        try {
            return restTemplate.exchange(url, HttpMethod.POST,httpEntity,type);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<?>getForUrl(String url,Class<?> type) {
        HttpEntity<?> httpEntity = getAuthorizationEntity();
        try {
            return restTemplate.exchange(url, HttpMethod.GET,httpEntity,type);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return null;
        }
    }

    private HttpEntity<?> getAuthorizationEntity() {
        String creds = sharedPrefsManager.getUserCred();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + creds);
        return new HttpEntity<>(headers);
    }
}
