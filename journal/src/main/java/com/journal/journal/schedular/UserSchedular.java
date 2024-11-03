package com.journal.journal.schedular;

import com.journal.journal.cache.AppCache;
import com.journal.journal.entity.JournalEntity;
import com.journal.journal.entity.User;
import com.journal.journal.enums.Sentiment;
import com.journal.journal.repository.UserRepositoryImpl;
import com.journal.journal.services.EmailService;
import com.journal.journal.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserSchedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 ? * SUN *")
    public  void fetchUserAndSendMail(){
        List<User> users=userRepository.getAllUserSA();
        for(User user:users){
            List<JournalEntity> journalEntities=user.getJournalEntries();
            List<Sentiment> sentiments=journalEntities.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
            for(Sentiment sentiment:sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry:sentimentCounts.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount= entry.getValue();
                    mostFrequentSentiment=entry.getKey();
                }
            }
            if(mostFrequentSentiment!=null){
                emailService.sendMail(user.getEmail(),"Sentiment Analysis for 7 days",mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * 1/1 * ? *")
    public void clearCache(){
        appCache.init();
    }
}
