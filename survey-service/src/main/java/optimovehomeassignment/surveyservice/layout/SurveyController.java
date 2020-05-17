package optimovehomeassignment.surveyservice.layout;



import lombok.extern.slf4j.Slf4j;

import optimovehomeassignment.surveyservice.models.ItemBoundary;
import optimovehomeassignment.surveyservice.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController

@Slf4j
public class SurveyController {


    private SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping(
            value = "/update/{dishId}",
            method = RequestMethod.PUT)
    public void updateVote(@PathVariable("dishId") String dishId){
        log.info("update item from poll-service");
        this.surveyService.update(dishId);
    }

    @RequestMapping(
            value = "/read",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemBoundary getStats(
            @RequestParam(name = "type", required = false, defaultValue = "data") String type
    ){
        return this.surveyService.read(type);
    }
}
