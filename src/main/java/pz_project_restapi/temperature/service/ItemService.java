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
    private TemperatureForm temperatureForm = new TemperatureForm();


    //final GET longest period by temperature
    public List<Item> getPeriodT() {
        List<Item> temporaryList = getAllItemsFromDatabase();
        List<ItemLDT> temporaryListLDT = convertToLocalDateTime(temporaryList);
        sortLocalDateTime(temporaryListLDT);
        float A = temperatureForm.getTemperatureA();
        float B = temperatureForm.getTemperatureB();

        List<ItemLDT> periodListLDT = longestPeriodByTemperature(temporaryListLDT, A, B);

        List<Item> periodList = convertToString(periodListLDT);
        return periodList;
    }


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
            Long tempId = item.get(i).getId();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(s, formatter);

            float f = item.get(i).getTemperature();
            ItemLDT tempItemLDT = new ItemLDT(tempId, localDateTime, f);
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
            Long tempId = itemLDT.get(i).getId();

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String formattedDateTime = ldt.format(formatter);

            float f = itemLDT.get(i).getTemperatureLDT();

            Item tempItem = new Item(tempId, formattedDateTime, f);
            temporaryStringStorage.add(tempItem);
        }
        return temporaryStringStorage;
    }


    // save temperatureEdges
    public TemperatureForm saveTemperatureEdges(TemperatureForm newEdges) {
        temperatureForm.setTemperatureA(newEdges.getTemperatureA());
        temperatureForm.setTemperatureB(newEdges.getTemperatureB());
        return temperatureForm;
    }

    //save temperature and time Edges

    public TemperatureForm saveTemperatureAndTimeEdges(TemperatureForm newEdges) {
        temperatureForm.setTemperatureA(newEdges.getTemperatureA());
        temperatureForm.setTemperatureB(newEdges.getTemperatureB());
        temperatureForm.setTimeX(newEdges.getTimeX());
        temperatureForm.setTimeY(newEdges.getTimeY());
        return temperatureForm;
    }
    
    //longest period in days, where temperature is between A and B
    public List<ItemLDT> longestPeriodByTemperature(List<ItemLDT> itemLDT,
                                                    float temperatureA, float temperatureB) {
        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < itemLDT.size(); i++) {
            if (((itemLDT.get(i).getTemperatureLDT() >= temperatureA) &&
                    (itemLDT.get(i).getTemperatureLDT() <= temperatureB))) {
                values.add(1);
            } else {
                values.add(0);
            }
        }
        List<Tuple> seqs = ItemService.longestEqualSeq(values);
        int theCount = seqs.get(0).getLength();
        int theIdx = seqs.get(0).getStart();
        List<ItemLDT> finalPeriod = new ArrayList<>();

        for (int i = theIdx; i < itemLDT.size(); i++) {
            finalPeriod.add(new ItemLDT(itemLDT.get(i).getId(), itemLDT.get(i).getLocalDateTime(),
                    itemLDT.get(i).getTemperatureLDT()));
        }
        List<ItemLDT> startEndPeriod = new ArrayList<>();
        startEndPeriod.add(finalPeriod.get(0));
        startEndPeriod.add(finalPeriod.get(finalPeriod.size()-1));
         return startEndPeriod;

    }

    //lond period in days, where tempereature is between A nad B and also
    // it was in interval between X and Y
//    public List<ItemLDT> longestPeriodByTemperatureAndTime
//    (List<ItemLDT> itemLDT, float temperatureA, float temperatureB,
//     LocalTime timeX, LocalTime timeY) {
//
//
//    }

    //convert String to LocalTime
    public LocalTime convertToLocalTime (String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(time, dtf);
        return localTime;
    }

    //GET longest period by termperature and time
//    public List<Item> getPeriodTeTi() {
//        List<Item> temporaryList = getAllItemsFromDatabase();
//        List<ItemLDT> temporaryListLDT = convertToLocalDateTime(temporaryList);
//        sortLocalDateTime(temporaryListLDT);
//
//        float A = temperatureForm.getTemperatureA();
//        float B = temperatureForm.getTemperatureB();
//        String x = temperatureForm.getTimeX();
//        String y = temperatureForm.getTimeY();
//        LocalTime timeX = convertToLocalTime(x);
//        LocalTime timeY = convertToLocalTime(y);
//
//        List<ItemLDT> periodListLDT = longestPeriodByTemperatureAndTime(temporaryListLDT, A, B, timeX, timeY);
//        List<Item> periodList = convertToString(periodListLDT);
//        return periodList;
//    }

    public static List<Tuple> longestEqualSeq(List<Integer> values) {
        int theCount = 0;   //number of the length of period
        int theIdx = 0;     //index in list
        int count = 1;      //variable for comparing
        List<Tuple> out = new ArrayList<Tuple>(); //list for saving

        for (int i = 1; i < values.size(); i++) {
            if (values.get(i - 1).equals(1) && (values.get(i).equals(1))) {
                count++;
                if (theCount < count) {
                    theCount = count;
                    theIdx = i;
                }
            } else {
                if (count > 1) {
                    out.add(new Tuple(theIdx - (theCount - 1), theCount));
                }
                count = 1;
                theCount = 0;
            }
        }
        if (count > 1) {
            out.add(new Tuple(theIdx - (theCount - 1), theCount));
        }
        out.sort(Comparator.comparing(Tuple::getLength).reversed());

        return out;
    }


}

/*      public List<ItemLDT> longestPeriodByTemperature(List<ItemLDT> itemLDT,
                                                    float temperatureA, float temperatureB) {
        List<ItemLDT> periodByTemperature = new ArrayList<>();

        for (int i = 0; i < itemLDT.size(); i++) {
            float tempTemperature = itemLDT.get(i).getTemperatureLDT();
            if((tempTemperature >= temperatureA)&&
                    (tempTemperature <= temperatureB)) {
                ItemLDT tempItemLdt = new ItemLDT(itemLDT.get(i).getId(), itemLDT.get(i).getLocalDateTime(),
                        itemLDT.get(i).getTemperatureLDT());
                periodByTemperature.add(tempItemLdt);
            } else {
            }
        }
        return periodByTemperature;
    }
 */

/*       public List<ItemLDT> longestPeriodByTemperatureAndTime
    (List<ItemLDT> itemLDT, float temperatureA, float temperatureB,
     LocalTime timeX, LocalTime timeY) {

     List<ItemLDT> periodByTemperatureTime = new ArrayList<>();

        for (int i = 0; i < itemLDT.size(); i++) {
            float tempTemperature = itemLDT.get(i).getTemperatureLDT();
            LocalDateTime dateTime = itemLDT.get(i).getLocalDateTime();
            LocalTime time = dateTime.toLocalTime();

            if(((tempTemperature >= temperatureA)&& (tempTemperature <= temperatureB)) &&
                    ((time.equals(timeX) || time.isAfter(timeX)) &&
                            ((time.equals(timeY) || time.isBefore(timeY)))))  {

                ItemLDT tempItemLdt = new ItemLDT(itemLDT.get(i).getId(), itemLDT.get(i).getLocalDateTime(),
                        itemLDT.get(i).getTemperatureLDT());
                periodByTemperatureTime.add(tempItemLdt);
            } else {
            }
        }
        return periodByTemperatureTime;
    }
 */
