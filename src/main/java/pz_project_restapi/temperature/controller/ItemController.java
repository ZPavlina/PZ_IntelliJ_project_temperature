package pz_project_restapi.temperature.controller;

import java.util.*;
import javax.net.ssl.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pz_project_restapi.temperature.exception.*;
import pz_project_restapi.temperature.model.*;
import pz_project_restapi.temperature.repository.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    //build create item REST API
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    //read item by id REST API
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
        updateItem.setDate(itemDetails.getDate());
        updateItem.setTime(itemDetails.getTime());

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
    


}
