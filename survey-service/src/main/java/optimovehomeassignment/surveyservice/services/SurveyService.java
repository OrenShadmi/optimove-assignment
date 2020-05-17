package optimovehomeassignment.surveyservice.services;


import optimovehomeassignment.surveyservice.models.ItemBoundary;

public interface SurveyService {
    ItemBoundary read(String type);

    void update(String foodId);

}
