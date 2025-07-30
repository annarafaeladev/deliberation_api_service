package br.com.deliberation_api.infrastructure.database.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "topic-collection-validation", order = "002", author = "anna.santana")
public class TopicCollectionValidationMigration {

    private final MongoTemplate mongoTemplate;

    public TopicCollectionValidationMigration(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        String collectionName = "topic";

        if (!mongoTemplate.collectionExists(collectionName)) {

            Document sessionSchema = new Document()
                    .append("bsonType", "object")
                    .append("required", java.util.Arrays.asList("openAt", "closeAt", "closedManually", "timeType", "duration", "createdAt", "updatedAt"))
                    .append("properties", new Document()
                            .append("openAt", new Document("bsonType", "date"))
                            .append("closeAt", new Document("bsonType", "date"))
                            .append("closedManually", new Document("bsonType", "bool"))
                            .append("timeType", new Document("bsonType", "string"))
                            .append("duration", new Document("bsonType", "int"))
                            .append("createdAt", new Document("bsonType", "date"))
                            .append("updatedAt", new Document("bsonType", "date"))
                    );

            Document optionsSchema = new Document()
                    .append("bsonType", "array")
                    .append("items", new Document("oneOf", java.util.Arrays.asList(
                            new Document("bsonType", "objectId"), // aceita ObjectId simples
                            new Document("bsonType", "object")    // aceita objeto DBRef
                                    .append("required", java.util.Arrays.asList("$ref", "$id"))
                                    .append("properties", new Document()
                                            .append("$ref", new Document("bsonType", "string"))
                                            .append("$id", new Document("bsonType", "objectId"))
                                    )
                    )));

            Document validator = new Document("$jsonSchema", new Document()
                    .append("bsonType", "object")
                    .append("required", java.util.Arrays.asList("title", "description", "options"))
                    .append("properties", new Document()
                            .append("title", new Document("bsonType", "string").append("description", "Title is required"))
                            .append("description", new Document("bsonType", "string").append("description", "Description is required"))
                            .append("session", sessionSchema) // assumindo que sessionSchema já está definido
                            .append("options", optionsSchema)
                    )
            );

            Document command = new Document("create", collectionName)
                    .append("validator", validator);

            mongoTemplate.executeCommand(command);
        }
    }

    @RollbackExecution
    public void rollback() {
        String collectionName = "topic";
        mongoTemplate.dropCollection(collectionName);
    }
}
