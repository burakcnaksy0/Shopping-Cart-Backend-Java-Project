package b.aksoy.shopcard.controller;

import b.aksoy.shopcard.dto.OrderDto;
import b.aksoy.shopcard.entity.Order;
import b.aksoy.shopcard.exception.OrderNotFoundException;
import b.aksoy.shopcard.response.ApiResponse;
import b.aksoy.shopcard.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {

            return ResponseEntity.ok(new ApiResponse("Item order successfully created",orderService.placeOrder(userId)));

    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {

            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item order successfully retrieved", order));
          }

    @GetMapping("/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {


            return ResponseEntity.ok(new ApiResponse("Item order successfully retrieved",orderService.getUserOrders(userId)));

    }


}
