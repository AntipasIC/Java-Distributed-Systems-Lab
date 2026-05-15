package order.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class InventoryEntity {
    @Id
    private String item;
    private int stock;

    @Version
    private long version;

    // Constructors
    public InventoryEntity() {}

    public InventoryEntity(String item, int stock) {
        this.item = item;
        this.stock = stock;
    }

    // Getters and Setters
    public String getItem() {
        return item;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
       