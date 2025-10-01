package com.coffee.repository;

import com.coffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 쿼리 메소드를 사용하여 특정 회원의 송장 번호가 큰 것(최신 주문) 것부터 조회
    // cf. 좀더 복잡한 쿼리를 사용하려면 @Query 또는 querydsl 사용
    List<Order> findByMemberIdOrderByIdDesc(Long memberId);

    // 주문번호(id) 기준으로 모든 주문 내역 역순(내림차순)으로 조회하려면 JPA 메소드를 작성
    List<Order> findAllByOrderByIdDesc(); // 관리자가 사용
}
