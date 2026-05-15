package order.service.repository;

import order.service.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository
    extends JpaRepository<InventoryEntity, String> {
    
}
