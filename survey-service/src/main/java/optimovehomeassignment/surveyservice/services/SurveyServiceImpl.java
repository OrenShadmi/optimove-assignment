package optimovehomeassignment.surveyservice.services;

import lombok.extern.slf4j.Slf4j;
import optimovehomeassignment.surveyservice.dao.ItemCrud;
import optimovehomeassignment.surveyservice.models.ItemBoundary;
import optimovehomeassignment.surveyservice.models.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    private static final String SOURCE_STR = "client";
    private final ItemCrud itemCrud;
    private RestClient restClient;
    private SumService sumService;

    Map<String, String> serverList;

    public static final String STATS = "stats";
    public static final String DATA = "data";




    @Autowired
    public SurveyServiceImpl(
            ItemCrud itemCrud,
            RestClient restClient,
            SumService sumService) {
        this.itemCrud = itemCrud;
        this.restClient = restClient;
        this.sumService = sumService;
        this.serverList = initMap();
    }

    private Map<String, String> initMap() {
        Map<String, String> map = new HashMap<>();

        /** server map to get data from */
        map.put("poll-service", "8081");
        return map;
    }

    @Override
    public void update(String id) {
        ItemEntity itemEntity = itemCrud.findById(id).orElseThrow(() -> new RuntimeException("no dish with this id"));
        itemEntity.setVotes(1 + itemEntity.getVotes());
        ItemEntity updatedItemEntity = this.itemCrud.save(itemEntity);
        log.info(this.getClass().getName() +" | Updated dish in db" + updatedItemEntity.toString());
    }


    @Override
    public ItemBoundary read(String type) {
        log.info("Reading items from: " + this.getClass().getName());

        ItemBoundary res = new ItemBoundary();
        switch (type){
            case STATS:
                this.sumService.initMap();

                /** iterate over server map to get data */
                for (Map.Entry<String,String> entry: this.serverList.entrySet()) {
                    List<ItemEntity> itemEntityList = restClient
                            .getItemsFromOtherService(entry.getKey(), entry.getValue());
                    this.sumService.sumVotesFromExternalService(itemEntityList);
                }

                /** Get items from DB */
                List<ItemEntity> itemEntityFromDB = (List<ItemEntity>) itemCrud.findAll();

                /** calc percentage */
                res.setItemsList(calcPercentage(
                        itemEntityFromDB.stream()
                            .map(i -> {
                                i.setVotes(sumService.getTotalVotesMap().get(i.getName()));
                                return i;
                             })
                            .collect(Collectors.toList())));
                break;
            case DATA:
                res.setItemsList((List<ItemEntity>) this.itemCrud.findAll());
                break;
            default:
                throw new RuntimeException("no type found");

        }
        return res;
    }

    private List<ItemEntity> calcPercentage(List<ItemEntity> itemEntityList) {
        double sum = itemEntityList.stream().mapToDouble(ItemEntity::getVotes).sum();
        log.info(this.getClass().getName() + " | Total votes: " + String.valueOf(sum));
        return itemEntityList.stream()
                .map(f -> {
                    f.setPercentageFromTotal((f.getVotes() / sum) * 100);
                    return f;
                })
                .collect(Collectors.toList());
    }
}
