package com.coffee.controller;

import com.coffee.dto.OrderDto;
import com.coffee.dto.OrderItemDto;
import com.coffee.entity.Member;
import com.coffee.entity.Order;
import com.coffee.entity.OrderProduct;
import com.coffee.entity.Product;
import com.coffee.service.CartProductService;
import com.coffee.service.MemberService;
import com.coffee.service.OrderService;
import com.coffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductService productService;
    private final CartProductService cartProductService;

    // 리엑트의 '카트 목록'이나 '주문하기' 버튼을 눌러 주문을 시도
    @PostMapping // CartList.js 파일의 makeOrder() 함수와 연관
    public ResponseEntity<?> order(@RequestBody OrderDto dto) {
        System.out.println(dto);

        // 회원(Member) 객체 생성
        Optional<Member> optionalMember = memberService.findMemberById(dto.getMemberId());
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("회원이 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        // 마일리지 적립 시스템을 넣는다면 이자리에서

        // 주문(Order) 객체 생성
        Order order = new Order();
        order.setMember(member); // 이 사람이 주문자
        order.setOrderdate(LocalDate.now()); // 주문시점 지금
        order.setStatus(dto.getStatus());

        // 주문 상품(OrderProduct)들은 확장 for 구문 사용
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (OrderItemDto item : dto.getOrderItems()) {
            // item은 주문하고자 하는 주문상품 1개를 의미
            Optional<Product> optionalProduct = productService.findProductById(item.getProductId());
            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("해당 상품이 존재하지 않습니다.");
            }
            Product product = optionalProduct.get();

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("재고 수량이 부족합니다.");
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setQuantity(item.getQuantity());
            orderProduct.setProduct(product);

            // 리스트 클렉션에 각 '주문 상품'을 담아줌
            orderProductList.add(orderProduct);

            // 상품의 재고 수량 빼기
            product.setStock(product.getStock() - item.getQuantity());

            // 카트에 담겨있던 품목을 삭제
            Long cartProductId = item.getCartProductId();

            if (cartProductId != null) {
                cartProductService.deleteCartProductById(cartProductId);
            }

        }
        order.setOrderProducts(orderProductList);

        // 주문 객체 저장
        orderService.saveOrder(order);

        String message = "주문이 완료 되었습니다.";
        return ResponseEntity.ok(message);
    }
}
