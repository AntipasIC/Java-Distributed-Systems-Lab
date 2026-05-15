package order.service.dto;

public record OrderRequest(
    String item,
    int quantity
) {
    
}
