package com.coffee.repository;

import com.coffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품 아이디 역순 목록 출력
    List<Product> findProductByOrderByIdDesc();

    // image 컬럼에 특정 문자열이 포함된 데이터를 조회
    // 데이터 베이스의 like 키워드와 유사
    // select * from products where image like '%big%';
    List<Product> findByImageContaining(String filter);
}
