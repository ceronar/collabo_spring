package com.coffee.service;

import com.coffee.entity.Product;
import com.coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 상품에 대한 로직 정보를 처리하는 서비스 클래스
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductList() {
        return this.productRepository.findProductByOrderByIdDesc();
    }

    public boolean deleteProduct(Long id) {
        // existsById() 메소드와 deleteById()메소드는 CRUDRepository에 포함
        if (productRepository.existsById(id)) { // 해당 항목이 존재하면
            productRepository.deleteById(id);
            return true; // 삭제 성공
        } else { // 존재하지 않음
            return false; // 실패
        }
    }
}
