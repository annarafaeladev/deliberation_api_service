package br.com.deliberation_api.infrastructure.database.migration;

import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "vote-collection-validation", order = "004", author = "anna.santana")
public class VoteCollectionValidationMigration {

    private final MongoTemplate mongoTemplate;

    public VoteCollectionValidationMigration(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        String collectionName = "vote";

        if (!mongoTemplate.collectionExists(collectionName)) {

            Document validator = new Document("$jsonSchema", new Document()
                    .append("bsonType", "object")
                    .append("required", java.util.Arrays.asList("topicId", "associateId", "optionId", "vote"))
                    .append("properties", new Document()
                                    .append("topicId", new Document("bsonType", "string").append("description", "must be a string and is required"))
                                    .append("associateId", new Document("bsonType", "string").append("description", "must be a string and is required"))
                                    .append("optionId", new Document("bsonType", "string").append("description", "must be a string and is required"))
                                    .append("vote", new Document("bsonType", "string").append("description", "must be a string and is required"))
                    )
            );

            Document command = new Document("create", collectionName)
                    .append("validator", validator);

            mongoTemplate.executeCommand(command);
        }
    }

    @RollbackExecution
    public void rollback() {
        String collectionName = "vote";
        mongoTemplate.getCollection(collectionName).dropIndex("topic_associate_option_unique_idx");
        mongoTemplate.dropCollection(collectionName);
    }
}