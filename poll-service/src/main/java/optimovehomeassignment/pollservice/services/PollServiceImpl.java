package optimovehomeassignment.pollservice.services;

import lombok.extern.slf4j.Slf4j;

import optimovehomeassignment.pollservice.dal.ItemCrud;
import optimovehomeassignment.pollservice.models.ItemBoundary;
import optimovehomeassignment.pollservice.models.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PollServiceImpl implements PollService{

    private static final String SOURCE_STR = "client";
    private final ItemCrud itemCrud;
    private RestClient restClient;
    private SumService sumService;

    Map<String, String> serverList;

    public static final String STATS = "stats";
    public static final String DATA = "data";




    @Autowired
    public PollServiceImpl(
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
        map.put("127.0.0.1", "8082");
        return map;
    }

    @Override
    public void update(String id) {
        ItemEntity itemEntity = itemCrud.findById(id).orElseThrow(() -> new RuntimeException("no item with this id"));
        itemEntity.setVotes(1 + itemEntity.getVotes());
        ItemEntity updatedItemEntity = this.itemCrud.save(itemEntity);
        log.info(this.getClass().getName() +" | Updated item in db" + updatedItemEntity.toString());
    }


    @Override
    public ItemBoundary read(String type) {
        log.info("Reading items from:" + this.getClass().getName());

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
        log.info(this.getClass().getName() + " | Total votes: " + sum);
        return itemEntityList.stream()
                .map(f -> {
                    f.setPercentageFromTotal((f.getVotes() / sum) * 100);
                    return f;
                })
                .collect(Collectors.toList());
    }
}
