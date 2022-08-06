package pz_project_restapi.temperature.service;

import java.time.*;
import java.time.format.*;
import java.util.*;
import org.springframework.format.annotation.*;
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
    private List<Edges> listOfTemperature = new ArrayList<>();


    //download all data for methods
    public synchronized List<Item> getAllItemsFromDatabase(Long id){
        List<Item> temporaryStorage = storage.findAll();
        for (Item item : temporaryStorage) {
            storageData.add(item);
        }
        return storageData;
    }

    //convert object Item String to object ItemLDT LocalDateTime
    public static List<ItemLDT> convertToLocalDateTime(List<Item> item) {
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
    public static List<Item> convertToString(List<ItemLDT> itemLDT) {
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

    //method for longest period without temperature changes


    

    //save temperatureEdges to the list
    public void saveEdges(DetailForm newEdges) {
        Edges addEdges = new Edges();
        addEdges.setTemperatureA(newEdges.getTemperatureA());
        addEdges.setTemperatureB(newEdges.getTemperatureB());
        listOfTemperature.add(addEdges);
    }



//    public void saveEdges(DetailForm temperatureEdges) {
//        Edges edges = clone(temperatureEdges);
//        listOfTemperature.add(edges);
//    }

    private Edges clone (Edges origin) {
        return new Edges((origin.getTemperatureA()), origin.getTemperatureB());
    }






    //longest period in days, where temperature is between A and B
    public static List<ItemLDT> longestPeriodByTemperature(List<ItemLDT> itemLDT,
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
    public static List<ItemLDT> longestPeriodByTemperatureAndTime
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
    
}
