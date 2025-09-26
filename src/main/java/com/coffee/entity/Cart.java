package com.coffee.entity;

import jakarta.persistence.*;
import lombok.*;

// 고객(Member)이 사용하는 가트 엔티티 클래스
@Getter @Setter @ToString
@Entity @Table(name = "carts")
@NoArgsConstructor @AllArgsConstructor
public class Cart {
    @Id // 이 컬럼
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 생성
    @Column(name = "cart_id") // 테이블에 생성되는 컬럼 이름
    private Long id; // 카트 id

    // 고객 1명이 1개의 카트를 사용.
    @OneToOne(fetch = FetchType.LAZY) // 일대일 연관 관계 매핑, 지연 로딩
    @JoinColumn(name = "member_id")
    private Member member;
}
