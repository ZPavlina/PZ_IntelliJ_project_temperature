package pz_project_restapi.temperature.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import pz_project_restapi.temperature.model.*;


public interface ItemRepository extends JpaRepository<Item, Long> {

    //all crud database methods
}
