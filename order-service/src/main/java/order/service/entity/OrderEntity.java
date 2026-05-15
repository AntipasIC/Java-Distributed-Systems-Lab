package order.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class OrderEntity {

    @Id
    private String id;
    private String item;
    private int quantity;
    private String idempotencyKey;

    protected OrderEntity() {
    }

    public OrderEntity(String item, int quantity, String idempotencyKey) {
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.quantity = quantity;
        this.idempotencyKey = idempotencyKey;
    }

    public String getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public String getIdempotencyKey() {
        return idempotencyKey;
    }
}
