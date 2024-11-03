package com.journal.journal.cache;

import com.journal.journal.entity.ConfigJournalAppEntity;
import com.journal.journal.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API_KEY;
    }

    public Map<String,String> appCache;

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct
    public  void init(){
        appCache=new HashMap<>();
        List<ConfigJournalAppEntity> all=configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity:all){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
