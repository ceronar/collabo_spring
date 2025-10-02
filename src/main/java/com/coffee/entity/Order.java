package com.coffee.entity;

import com.coffee.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @ToString
@Entity
@Table(name = "orders") // 주의) order는 데이터 베이스 전용 키워드라 생성 불가
public class Order { // 주문 관련 Entitiy
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    // 고객 한명이 여러개의 주문을 할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 통상적으로 주문을 할 때 여러개의 '주문 상품'을 동시에 주문
    // 하나의 주문에는 '주문 상품'을 여러개 담을 수 있음
    // 주의) mappedBy 항목의 "order"는 OrderProduct에 들어있는 Order 타입의 변수명
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

    private LocalDate orderdate; // 주문 날짜

    @Enumerated(EnumType.STRING) // DB에 Enum의 문자로 넣음 (기본적으론 숫자)
    private OrderStatus status; // 주문 상태
}
