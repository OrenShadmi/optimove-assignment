package optimovehomeassignment.pollservice;

import optimovehomeassignment.pollservice.dal.ItemCrud;
import optimovehomeassignment.pollservice.models.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Demo implements CommandLineRunner {

    private ItemCrud itemCrud;

    @Autowired
    public Demo(ItemCrud itemCrud) {
        this.itemCrud = itemCrud;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.itemCrud.deleteAll();
//        initFoodInDB();
    }

    private void initFoodInDB() {

        this.itemCrud.saveAll(
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
