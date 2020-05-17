package optimovehomeassignment.surveyservice;

import optimovehomeassignment.surveyservice.dao.ItemCrud;
import optimovehomeassignment.surveyservice.models.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Demo implements CommandLineRunner {

    private ItemCrud dishCrud;

    @Autowired
    public Demo(ItemCrud dishCrud) {
        this.dishCrud = dishCrud;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.dishCrud.deleteAll();
//        initFoodInDB();
    }

    private void initFoodInDB() {

        this.dishCrud.saveAll(
                        Stream.of("Pizza",
                                "Pasta",
                                "Salmon",
                                "Fries",
                                "Steak",
                                "Eggs",
                                "Salad",
                                "Burger")
                                .map(name -> new ItemEntity(name, 0, 0))
                                .collect(Collectors.toList())
      );
    }
}
