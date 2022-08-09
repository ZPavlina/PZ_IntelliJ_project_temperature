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

	}
}
