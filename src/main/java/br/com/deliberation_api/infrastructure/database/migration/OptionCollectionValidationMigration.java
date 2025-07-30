package br.com.deliberation_api.infrastructure.database.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "option-collection-migration", order = "003", author = "anna.santana")
public class OptionCollectionValidationMigration {

    private final MongoTemplate mongoTemplate;

    public OptionCollectionValidationMigration(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        String collectionName = "option";

        if (!mongoTemplate.collectionExists(collectionName)) {

            Document validator = new Document();
            validator.append("$jsonSchema", new Document()
                    .append("bsonType", "object")
                    .append("required", java.util.Arrays.asList("title", "description"))
                    .append("properties", new Document()
                            .append("title", new Document()
                                    .append("bsonType", "string")
                                    .append("description", "Title is required and must be a string"))
                            .append("description", new Document()
                                    .append("bsonType", "string")
                                    .append("description", "Description is required and must be a string"))
                    )
            );

            Document command = new Document("create", "option")
                    .append("validator", validator);

            mongoTemplate.executeCommand(command);
        }
    }

    @RollbackExecution
    public void rollback() {
         mongoTemplate.dropCollection("option");
    }
}

