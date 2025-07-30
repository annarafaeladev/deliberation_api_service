package br.com.deliberation_api.infrastructure.database.migration;

import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "view-collection-validation", order = "005", author = "anna.santana")
public class ViewCollectionValidationMigration {

    private final MongoTemplate mongoTemplate;

    public ViewCollectionValidationMigration(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        String collectionName = "view";

        if (!mongoTemplate.collectionExists(collectionName)) {

            Document validator = new Document("$jsonSchema", new Document()
                    .append("bsonType", "object")
                    .append("required", java.util.Arrays.asList("type", "name", "title", "visible"))
                    .append("properties", new Document()
                            .append("type", new Document("bsonType", "string"))
                            .append("name", new Document("bsonType", "string"))
                            .append("title", new Document("bsonType", "string"))
                            .append("text", new Document("bsonType", "string"))
                            .append("elements", new Document()
                                    .append("bsonType", "array")
                                    .append("items", new Document("bsonType", "object"))
                            )
                            .append("buttonOk", new Document("bsonType", "object"))
                            .append("buttonCancel", new Document("bsonType", "object"))
                            .append("visible", new Document("bsonType", "bool"))
                            .append("createdAt", new Document("bsonType", "date"))
                            .append("updatedAt", new Document("bsonType", "date"))
                    )
            );

            Document command = new Document("create", collectionName)
                    .append("validator", validator);

            mongoTemplate.executeCommand(command);

        }
    }

    @RollbackExecution
    public void rollback() {
        String collectionName = "view";
        mongoTemplate.dropCollection(collectionName);
    }
}
