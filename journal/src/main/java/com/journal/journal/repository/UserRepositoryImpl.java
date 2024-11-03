package com.journal.journal.repository;

import com.journal.journal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getAllUserSA(){
        Query query=new Query();
        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("email").ne(""));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users= mongoTemplate.find(query,User.class);
        return users;
    }
}
