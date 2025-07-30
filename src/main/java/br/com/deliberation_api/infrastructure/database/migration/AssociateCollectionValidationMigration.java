package br.com.deliberation_api.infrastructure.database.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "associate-collection-validation", order = "001", author = "anna.santana")
public class AssociateCollectionValidationMigration {

    private final MongoTemplate mongoTemplate;

    public AssociateCollectionValidationMigration(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        String collectionName = "associate";

        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName);

            Document validator = new Document();
            validator.append("$jsonSchema", new Document()
                    .append("bsonType", "object")
                    .append("required", java.util.Arrays.asList("name", "document"))
                    .append("properties", new Document()
                            .append("properties", new Document()
                                    .append("name", new Document()
                                            .append("bsonType", "string")
                                            .append("description", "name is required and must be a string"))
                                    .append("document", new Document()
                                            .append("bsonType", "string")
                                            .append("description", "document is required and must be a string"))
                            )
                    )
            );

            Document command = new Document("create", "associate")
                    .append("validator", validator);

            mongoTemplate.executeCommand(command);

            Document initialDoc = new Document()
                    .append("name", "Anna Rafaela")
                    .append("document", "11155737482");

            mongoTemplate.insert(initialDoc, collectionName);
        }
    }

    @RollbackExecution
    public void rollback() {
        String collectionName = "associate";
        mongoTemplate.getCollection(collectionName).dropIndex("unique_document_idx");
    }
}
