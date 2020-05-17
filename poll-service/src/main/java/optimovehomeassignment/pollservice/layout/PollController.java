package optimovehomeassignment.pollservice.layout;



import lombok.extern.slf4j.Slf4j;
import optimovehomeassignment.pollservice.models.ItemBoundary;
import optimovehomeassignment.pollservice.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PollController {


    private PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @RequestMapping(
            value = "/update/{itemId}",
            method = RequestMethod.PUT)
    public void updateVote(@PathVariable("itemId") String itemId){
        log.info("update item from poll-service");
        this.pollService.update(itemId);
    }

    @RequestMapping(
            value = "/read",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemBoundary getStats(
            @RequestParam(name = "type", required = false, defaultValue = "data") String type
    ){
        return this.pollService.read(type);
    }
}
