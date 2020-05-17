package optimovehomeassignment.surveyservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("DISH")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemEntity {

    @Id
    private String id;
    private String name;
    private int votes;
    private double percentageFromTotal;

    public ItemEntity(String name, int votes, double percentageFromTotal) {
        this.name = name;
        this.votes = votes;
        this.percentageFromTotal = percentageFromTotal;
    }
}
