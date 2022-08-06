package pz_project_restapi.temperature.service;

import java.time.*;
import java.util.*;
import pz_project_restapi.temperature.model.*;

public interface IItemService {


    List<Item> getAllItemsFromDatabase();

    List<ItemLDT> convertToLocalDateTime(List<Item> item);

    List<ItemLDT> sortLocalDateTime(List<ItemLDT> itemLDT);

    List<Item> convertToString(List<ItemLDT> itemLDT)   ;

    TemperatureForm saveEdges(TemperatureForm newEdges);

    List<ItemLDT> longestPeriodByTemperature(List<ItemLDT> itemLDT, float temperatureA, float temperatureB);

    List<ItemLDT> longestPeriodByTemperatureAndTime(List<ItemLDT> itemLDT, float temperatureA,
                                                    float temperatureB, LocalTime timeX, LocalTime timeY);




}
