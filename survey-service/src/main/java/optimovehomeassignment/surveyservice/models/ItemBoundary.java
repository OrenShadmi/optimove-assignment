package optimovehomeassignment.surveyservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemBoundary {

    private List<ItemEntity> itemsList;
}
