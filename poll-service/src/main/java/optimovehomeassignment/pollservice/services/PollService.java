package optimovehomeassignment.pollservice.services;


import optimovehomeassignment.pollservice.models.ItemBoundary;

public interface PollService {

    ItemBoundary read(String stats);

    void update(String itemId);

}
