package pz_project_restapi.temperature.service;

import java.time.*;
import java.time.format.*;
import java.util.*;
import javax.print.attribute.standard.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.format.annotation.*;
import org.springframework.stereotype.*;
import net.bytebuddy.asm.*;
import pz_project_restapi.temperature.model.*;
import pz_project_restapi.temperature.repository.*;


@Component
public class ItemService implements IItemService{

    private ItemRepository storage;

    private List<Item> storageData = new ArrayList<>();
//    private List<LocalDate> storageLocalDate = new ArrayList<>();


    public ItemService(ItemRepository storage) {
        this.storage = storage;
    }


    //download all data for methods
    public synchronized List<Item> getAllItemsFromDatabase(Long id){
        List<Item> temporaryStorage = storage.findAll();
        for (Item item : temporaryStorage) {
            storageData.add(item);
        }
        return storageData;
    }

    //convert String to LocalDate
    public List<LocalDateTime> convertToLocalDateTime(List<Item> item) {
        List<LocalDateTime> temporaryStorageLocalDateTime = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {
            LocalDateTime localDateTime = LocalDateTime.parse(item.get(i).getDateAndTime());
            temporaryStorageLocalDateTime.add(localDateTime);
        }
        temporaryStorageLocalDateTime.sort(Comparator.naturalOrder());
        return temporaryStorageLocalDateTime;
    }

    //longest period in days, where temperature is between A and B
       




    //lond period in days, where tempereature is between A nad B and also
    // it was in interval between X and Y





}
