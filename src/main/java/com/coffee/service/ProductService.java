package com.coffee.service;

import com.coffee.entity.Product;
import com.coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void save(Product product) {
        // save() 메소드는 CRUDRepository에 포함
        this.productRepository.save(product);
    }

    public Product getProductById(Long id) {
        // findById() 메소드는 CRUDRepository에 포함
        // Optional<>을 반환
        // Optional : 해당 상품이 있을 수도 있지만 경우에 따라 없을수도 있음
        Optional<Product> product = productRepository.findById(id);

        // 의미 있는 데이터면 넘기고 아니면 null 반환
        return product.orElse(null);
    }
}
