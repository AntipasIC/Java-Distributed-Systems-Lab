package order.service.controller;

import order.service.dto.OrderRequest;
import order.service.dto.OrderResponse;
import order.service.entity.InventoryEntity;
import order.service.entity.OrderEntity;
import order.service.repository.OrderRepository;
import order.service.repository.InventoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;

    public OrderController(
        OrderRepository orderRepository, 
        InventoryRepository inventoryRepository
    ) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    @PostMapping("/orders")
    public OrderResponse createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("idempotency-key") String requestId
    ) {
        
        System.out.println("[OrderService] Request ID: " + requestId);
        Optional<OrderEntity> existing =
        orderRepository.findByIdempotencyKey(requestId);

        if (existing.isPresent()) {

            System.out.println("Duplicate request detected");

            return new OrderResponse(
                    existing.get().getId(),
                    "ALREADY_EXISTS"
            );
        }

        InventoryEntity inventory =
        inventoryRepository.findById(request.item())
                .orElseThrow();

        if (inventory.getStock() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Out of stock");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        inventory.setStock(inventory.getStock() - 1);

        inventoryRepository.save(inventory);

        OrderEntity order =
                new OrderEntity(
                        request.item(),
                        request.quantity(),
                        requestId
                );

        orderRepository.save(order);

        System.out.println("Saved order: " + order.getId());
        
        /*int delay = new Random().nextInt(4000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }*/
        return new OrderResponse(
                order.getId(),
                "CREATED"
        );
    }

    @GetMapping("/orders")
    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }
}