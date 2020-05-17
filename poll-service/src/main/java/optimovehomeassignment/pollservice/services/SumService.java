package optimovehomeassignment.pollservice.services;

import optimovehomeassignment.pollservice.dal.ItemCrud;
import optimovehomeassignment.pollservice.models.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SumService {

    private  ItemCrud itemCrud;
    private Map<String,Integer> totalVotesMap;

    @Autowired
    public SumService(ItemCrud itemCrud) {
        this.itemCrud = itemCrud;
    }



    public void initMap(){
        this.totalVotesMap = new HashMap<>();
        ArrayList<ItemEntity> itemEntities = (ArrayList<ItemEntity>) itemCrud.findAll();
        itemEntities.forEach(i-> totalVotesMap.put(i.getName(), i.getVotes()));
    }


    public void sumVotesFromExternalService(List<ItemEntity> itemList) {
        itemList.forEach(i -> {
            if(totalVotesMap.containsKey(i.getName())){
                int totalVotes = totalVotesMap.get(i.getName()) + i.getVotes();
                totalVotesMap.put(i.getName(), totalVotes);
            }
        });
    }

    public Map<String, Integer> getTotalVotesMap() {
        return totalVotesMap;
    }
}
