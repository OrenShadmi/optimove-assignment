package optimovehomeassignment.surveyservice.services;

import lombok.extern.slf4j.Slf4j;
import optimovehomeassignment.surveyservice.models.ItemBoundary;
import optimovehomeassignment.surveyservice.models.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class RestClient {

    RestTemplate restTemplate;

    @Autowired
    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ItemEntity> getItemsFromOtherService(String ip, String port){
        ItemBoundary itemBoundary = this.restTemplate.getForObject(
                String.format("http://%s:%s/read?type=data", ip, port), ItemBoundary.class);
        assert itemBoundary != null;
        log.info(this.getClass().getName() + " | Reading items from poll service" + itemBoundary.toString());
        return itemBoundary.getItemsList();
    }


}
