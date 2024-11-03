package com.journal.journal.services;

import com.journal.journal.api.response.WeatherApiResponse;
import com.journal.journal.cache.AppCache;
import com.journal.journal.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

//    Get Method
    public WeatherApiResponse getWeather(String city){
        WeatherApiResponse weatherApiResponse=redisService.get("weather_of_"+city, WeatherApiResponse.class);
        if(weatherApiResponse!=null){
            return weatherApiResponse;
        }else{
            String finalApi=appCache.appCache.get(AppCache.keys.WEATHER_API_KEY.toString()).replace(Placeholders.City,city).replace(Placeholders.API_KEY,apiKey);
            ResponseEntity<WeatherApiResponse> response=  restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherApiResponse.class);
            WeatherApiResponse body=response.getBody();
            if(body!=null){
                redisService.set("weather_of_"+city,body,300l);
            }
            return body;
        }

    }

//    Post Method
//    public WeatherApiResponse getWeather(String city){
//        String finalApi=API.replace("City",city).replace("API_KEY",apiKey);
//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.set("key","value");
//        User user= User.builder().username("rahul").password("123").build();
//        HttpEntity<User> httpEntity=new HttpEntity<>(user,httpHeaders);
//        ResponseEntity<WeatherApiResponse> response=  restTemplate.exchange(finalApi, HttpMethod.POST,httpEntity, WeatherApiResponse.class);
//        WeatherApiResponse body=response.getBody();
//        return body;
//    }
}
