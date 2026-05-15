package gateway.service.controller;

import gateway.service.dto.OrderRequest;
import gateway.service.dto.OrderResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;
import org.springframework.http.*;

@RestController
public class OrderController {
    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/place-order")
    public OrderResponse placeOrder(@RequestBody OrderRequest request) {

        String requestId = UUID.randomUUID().toString();

        System.out.println("[Gateway] Request ID: " + requestId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Idempotency-Key", requestId);

        HttpEntity<OrderRequest> entity =
                new HttpEntity<>(request, headers);

        String orderServiceUrl = "http://localhost:8081/orders";

        try {

            ResponseEntity<OrderResponse> response =
                    restTemplate.exchange(
                            orderServiceUrl,
                            HttpMethod.POST,
                            entity,
                            OrderResponse.class
                    );

            return response.getBody();

        } catch (Exception e) {

            System.out.println("[Gateway] Retrying request...");

            ResponseEntity<OrderResponse> retryResponse =
                    restTemplate.exchange(
                            orderServiceUrl,
                            HttpMethod.POST,
                            entity,
                            OrderResponse.class
                    );

            return retryResponse.getBody();
        }
    }
}
