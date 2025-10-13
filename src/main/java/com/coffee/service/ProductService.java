package com.coffee.service;

import com.coffee.dto.SearchDto;
import com.coffee.entity.Product;
import com.coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // 상품에 대한 여러 가지 로직 정보를 처리해주는 서비스 클래스입니다.
public class ProductService {
    @Autowired
    private ProductRepository productRepository ;

    public List<Product> getProductList() {
        return this.productRepository.findProductByOrderByIdDesc();
    }

    public boolean deleteProduct(Long id) {
        // existsById() 메소드와 deleteById() 메소드는 CrudRepository에 포함되어 있습니다.
        if(productRepository.existsById(id)){ // 해당 항목이 존재하면
            this.productRepository.deleteById(id); // 삭제하기
            return true ; // true의 의미는 "삭제 성공" 했습니다.

        }else{ // 존재하지 않으면
            return false ;
        }
    }

    public void save(Product product) {
        // save() 메소드는 CrudRepository에 포함되어 있습니다.
        this.productRepository.save(product);
    }

    public Product getProductById(Long id) {
        // findById() 메소드는 CrudRepository에 포함되어 있습니다.
        // 그리고, Optional<>을 반환합니다.
        // Optional : 해당 상품이 있을 수도 있지만, 경우에 따라서 없을 수도 있습니다.
        Optional<Product> product = this.productRepository.findById(id);

        // 의미 있는 데이터이면 그냥 넘기고, 그렇지 않으면 null을 반환해 줍니다.
        return product.orElse(null);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findProductById(Long productId) {
        return this.productRepository.findById(productId);
    }

    public List<Product> getProductsByFilter(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return productRepository.findByImageContaining(filter);
        }
        return productRepository.findAll();
    }

    public Page<Product> listProduct(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    // 필드 검색 조건과 페이징 기본 정보를 사용하여 상품 목록 조회
    public Page<Product> listProducts(SearchDto searchDto, int pageNumber, int pageSize) {
        // Specification : 엔티티 객체에 대한 쿼리 조건을 정의할 수 있는 조건자(Specification)로 사용
        Specification<Product> spec = Specification.where(null); // 조건 없음

        // 기간 검색 콤보 박스의 조건 추가
        if (searchDto.getSearchDateType() != null) {
            spec = spec.and(ProductSpecification.hasDateRange(searchDto.getSearchDateType()));
        }

        // 카테고리의 조건 추가
        if (searchDto.getCategory() != null) {
            spec = spec.and(ProductSpecification.hasCategory(searchDto.getCategory()));
        }

        // 검색 모드에 따른 조건 추가(name or description)
        String searchMode = searchDto.getSearchMode();
        String searchKeyword = searchDto.getSearchKeyword();

        if (searchMode != null && searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            if ("name".equals(searchMode)) { // 상품명으로 검색
                spec = spec.and(ProductSpecification.hasNameLike(searchKeyword));
            } else if ("description".equals(searchMode)) { // 상품 설명으로 검색
                spec = spec.and(ProductSpecification.hasDescriptionLike(searchKeyword));
            }
        }
        // 상품의 id를 역순으로 정렬
        Sort sort = Sort.by(Sort.Order.desc("id"));

        // pageNumber 페이지(0 base) pageSize개씩 sort 방식 정렬로 조회
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return this.productRepository.findAll(spec, pageable);
    }
}
