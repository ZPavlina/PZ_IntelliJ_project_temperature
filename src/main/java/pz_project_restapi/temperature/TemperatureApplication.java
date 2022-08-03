package pz_project_restapi.temperature;

import java.time.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import pz_project_restapi.temperature.model.*;
import pz_project_restapi.temperature.repository.*;

@SpringBootApplication
public class TemperatureApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureApplication.class, args);
	}

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public void run(String... args) throws Exception {

//
//		Item item1 = new Item();
//		item1.setTemperature(28.9f);
//		item1.setDate("2022-04-01");
//		item1.setTime("13:30");
//		itemRepository.save(item1);
//
//		Item item2 = new Item();
//		item2.setTemperature(28.9f);
//		item2.setDate("2022-04-02");
//		item2.setTime("7:30");
//		itemRepository.save(item2);



		
	}
}
