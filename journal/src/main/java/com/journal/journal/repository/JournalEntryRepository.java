package com.journal.journal.repository;

import com.journal.journal.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntity, ObjectId> {

}
