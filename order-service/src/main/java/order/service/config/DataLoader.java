package order.service.config;

import order.service.entity.InventoryEntity;
import order.service.repository.InventoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class DataLoader {
    private final InventoryRepository inventoryRepository;

    public DataLoader(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostConstruct
    public void loadData() {

        inventoryRepository.save(
                new InventoryEntity("Laptop", 1)
        );

        System.out.println("Inventory loaded");
    }    

}
