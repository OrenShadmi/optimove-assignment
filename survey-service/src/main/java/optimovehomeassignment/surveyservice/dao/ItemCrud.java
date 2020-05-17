package optimovehomeassignment.surveyservice.dao;

import optimovehomeassignment.surveyservice.models.ItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemCrud extends CrudRepository<ItemEntity, String> {


}
