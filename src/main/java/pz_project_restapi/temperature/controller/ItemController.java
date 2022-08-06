package pz_project_restapi.temperature.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pz_project_restapi.temperature.exception.*;
import pz_project_restapi.temperature.model.*;
import pz_project_restapi.temperature.repository.*;
import pz_project_restapi.temperature.service.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/pz/items")

public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ItemService itemService;


    @GetMapping
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    //build create item REST API
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    //build read item by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Item> getItemById(@PathVariable long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id:" + id));
        return ResponseEntity.ok(item);
    }

    //build update item by id REST API
    @PutMapping("{id}")
    public ResponseEntity<Item> upddateItem(@PathVariable long id, @RequestBody Item itemDetails) {
        Item updateItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id:" + id));
        updateItem.setTemperature(itemDetails.getTemperature());
        updateItem.setDateAndTime(itemDetails.getDateAndTime());
        itemRepository.save(updateItem);
        return ResponseEntity.ok(updateItem);
    }

    //delete item by id REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id:" + id));
        itemRepository.delete(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
                                              
    // temperature edges for longest period
    @PostMapping("{temperatureForm}")
    public TemperatureForm saveTemperatureEdges(@RequestBody TemperatureForm temperatureForm) {
        return itemService.saveTemperatureEdges(temperatureForm);
    }

    //GET nejdelsi obdobi ve dnech mezi teplotami A a B
    @GetMapping("/period/{temperature}")
    public List<Item> longestPeriod () {
        return itemService.getPeriod();
    }

    //temperature and time edges for longest period
    @PostMapping("/form/{TemperatureTime}")
    public TemperatureForm saveTemperatureAndTimeEdges(@RequestBody TemperatureForm temperatureForm) {
         return itemService.saveTemperatureAndTimeEdges(temperatureForm);
    }


}
