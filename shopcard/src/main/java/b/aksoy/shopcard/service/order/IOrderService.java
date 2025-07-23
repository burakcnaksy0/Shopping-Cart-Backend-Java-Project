package b.aksoy.shopcard.service.order;

import b.aksoy.shopcard.dto.OrderDto;
import b.aksoy.shopcard.entity.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);


    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
