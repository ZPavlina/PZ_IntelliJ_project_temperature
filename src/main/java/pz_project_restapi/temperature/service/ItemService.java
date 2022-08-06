package pz_project_restapi.temperature.service;

import java.time.*;
import java.time.format.*;
import java.util.*;
import org.springframework.stereotype.*;
import pz_project_restapi.temperature.model.*;
import pz_project_restapi.temperature.repository.*;



@Component
public class ItemService implements IItemService{


    private ItemRepository storage;

    public ItemService(ItemRepository storage) {
        this.storage = storage;
    }

    private List<Item> storageData = new ArrayList<>();
    private List<ItemLDT> storageLocalDateTime = new ArrayList<>();
    private TemperatureForm temperatureForm = new TemperatureForm();


    //download and save all data from databases for methods longest period
    public synchronized List<Item> getAllItemsFromDatabase(){
        List<Item> temporaryStorage = storage.findAll();
        for (Item item : temporaryStorage) {
            storageData.add(item);
        }
        return storageData;
    }

    //convert object Item String to object ItemLDT LocalDateTime
    public List<ItemLDT> convertToLocalDateTime(List<Item> item) {
        List<ItemLDT> temporaryStorageLocalDateTime = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {
            String s = item.get(i).getDateAndTime();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime localDate = LocalDateTime.parse(s, formatter);

            float f = item.get(i).getTemperature();
            ItemLDT tempItemLDT = new ItemLDT(localDate, f);
            temporaryStorageLocalDateTime.add(tempItemLDT);
        }
        return temporaryStorageLocalDateTime;
    }

    //sort List of object ItemLDT LocalDateTime
    public List<ItemLDT> sortLocalDateTime(List<ItemLDT> itemLDT) {
        itemLDT.sort(Comparator.comparing(ItemLDT::getLocalDateTime));
        return itemLDT;
    }

    //conveert object LocalDate to object String
    public List<Item> convertToString(List<ItemLDT> itemLDT) {
        List<Item> temporaryStringStorage = new ArrayList<>();

        for (int i = 0; i < itemLDT.size() ; i++) {
            LocalDateTime ldt = itemLDT.get(i).getLocalDateTime();

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String formattedDateTime = ldt.format(formatter);

            float f = itemLDT.get(i).getTemperatureLDT();

            Item tempItem = new Item(formattedDateTime, f);
            temporaryStringStorage.add(tempItem);
        }
        return temporaryStringStorage;
    }


//    save temperatureEdges to the list
    public TemperatureForm saveEdges(TemperatureForm newEdges) {

        temperatureForm.setTemperatureA(newEdges.getTemperatureA());
        temperatureForm.setTemperatureB(newEdges.getTemperatureB());

        return temperatureForm;
    }
    
    //longest period in days, where temperature is between A and B
    public List<ItemLDT> longestPeriodByTemperature(List<ItemLDT> itemLDT,
                                                    float temperatureA, float temperatureB) {
        List<ItemLDT> periodByTemperature = new ArrayList<>();

        for (int i = 0; i < itemLDT.size(); i++) {
            float tempTemperature = itemLDT.get(i).getTemperatureLDT();
            if((tempTemperature >= temperatureA)&&
                    (tempTemperature <= temperatureB)) {
                ItemLDT tempItemLdt = new ItemLDT(itemLDT.get(i).getLocalDateTime(),
                        itemLDT.get(i).getTemperatureLDT());
                periodByTemperature.add(tempItemLdt);
            } else {
            }
        }
        return periodByTemperature;
    }

    //lond period in days, where tempereature is between A nad B and also
    // it was in interval between X and Y
    public List<ItemLDT> longestPeriodByTemperatureAndTime
    (List<ItemLDT> itemLDT, float temperatureA, float temperatureB,
     LocalTime timeX, LocalTime timeY) {

        List<ItemLDT> periodByTemperatureTime = new ArrayList<>();

        for (int i = 0; i < itemLDT.size(); i++) {
            float tempTemperature = itemLDT.get(i).getTemperatureLDT();
            LocalDateTime dateTime = itemLDT.get(i).getLocalDateTime();
            LocalTime time = dateTime.toLocalTime();

            if(((tempTemperature >= temperatureA)&& (tempTemperature <= temperatureB)) &&
                    ((time.isAfter(timeX)) && (time.isBefore(timeY))))  {

                ItemLDT tempItemLdt = new ItemLDT(itemLDT.get(i).getLocalDateTime(),
                        itemLDT.get(i).getTemperatureLDT());
                periodByTemperatureTime.add(tempItemLdt);
            } else {
            }
        }
        return periodByTemperatureTime;
    }

    //final GET longest period
    public List<Item> getPeriod() {
        List<Item> temporaryList = getAllItemsFromDatabase();
        List<ItemLDT> temporaryListLDT = convertToLocalDateTime(temporaryList);
        sortLocalDateTime(temporaryListLDT);
        float A = temperatureForm.getTemperatureA();
        float B = temperatureForm.getTemperatureB();

        List<ItemLDT> periodListLDT = longestPeriodByTemperature(temporaryListLDT, A, B);
        List<Item> periodList = convertToString(periodListLDT);
        return periodList;
    }
    
}
