package com.coffee.controller;

import com.coffee.entity.Product;
import com.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Value("${productImageLocation}")
    private String productImageLocation; // 기본값 : null

    @Autowired
    private ProductService productService;

    @GetMapping("/list") // 상품 목록 List Collection 반환
    public List<Product> list() {
        List<Product> products = this.productService.getProductList();

        return products;
    }

    // 클라이언트가 특정 상품 id에 대하여 "삭제" 요청
    // @PathVariable : URL의 경로 변수를 메소드의 매개 변수로 값을 전달
    @DeleteMapping("/delete/{id}") // {id} : 경로 변수, 가변 매개 변수로 이해하면 됨
    public ResponseEntity<String> delete(@PathVariable Long id) { // {id}로 넘겨온 상품의 아이디가 변수 id에 할당
        try {
            boolean isDeleted = productService.deleteProduct(id);

            if(isDeleted) {
                return ResponseEntity.ok(id + "번 상품이 삭제 되었습니다.");
            } else {
                return ResponseEntity.badRequest().body(id + "번 상품이 존재하지 않습니다.");
            }
        } catch (Exception err) {
            return ResponseEntity.internalServerError().body("오류 발생 : " + err.getMessage());
        }
    }

    // 상품 등록
    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody Product product) {
        // @RequestBody : http 를 이용하여 넘어온 데이터(body)로 자바 객체 형식으로 반환

        // 데이터 베이스와 이미지 경로에 저장될 이미지의 이름
        String imageFileName = "product_" + System.currentTimeMillis() + ".jpg";

        // 폴더 구분자가 제대로 설정 되어 있으면 그대로 사용
        // 그렇지 않으면 폴더 구분자를 붙여줌
        // File.separator : 폴더 구분자를 의미하며, 리눅스는 /, 윈도우는 \
        String pathName = productImageLocation.endsWith("\\") || productImageLocation.endsWith("/")
                ? productImageLocation
                : productImageLocation + File.separator;

        File imageFile = new File(pathName + imageFileName);
        String imageData = product.getImage(); // Base64 인코딩 문자열

        try {
            // 파일 정보를 byte 단위로 변환하여 이미지를 복사
            FileOutputStream fos = new FileOutputStream(imageFile);

            // 메소드 체이닝 : .을 연속적으로 찍어서 메소드를 계속 호출
            byte[] decodedImage = Base64.getDecoder().decode(imageData.split(",")[1]);
            fos.write(decodedImage); // 바이트 파일을 해당 이미지 경로에 복사

            product.setImage(imageFileName);
            product.setInputdate(LocalDate.now());

            this.productService.save(product);

            return ResponseEntity.ok(Map.of("message", "Product insert successfully", "image", imageFileName));

        } catch (Exception err) {
            return ResponseEntity.status(500).body(Map.of("message", err.getMessage(), "error", "Error file uploading"));
        }
    }

    // FrontEnd의 상품 수정 페이지에서 요청
    @GetMapping("/update/{id}") // 상품의 id 정보를 이용하여 해당 상품 Bean 객체를 반환해줌
    public ResponseEntity<Product> getUpdate(@PathVariable Long id) {
        System.out.println("수정할 상품 번호 : " + id);

        Product product = productService.getProductById(id);

        if (product == null) { // 상품이 없으면 404 응답과 함께 null 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else { // 해당 상품의 정보와 함께 성공(200) 메세지를 반환
            return ResponseEntity.ok(product);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product updateProduct) {
        Product product = productService.getProductById(id);

        if (product == null) { // 상품이 존재하지 않으면 404 응답 반환
            return ResponseEntity.notFound().build();
        }

        String pathName = productImageLocation.endsWith("\\") || productImageLocation.endsWith("/")
                ? productImageLocation
                : productImageLocation + File.separator;

        // 1. 기존 이미지 삭제
        String oldImagePath = product.getImage();
        if (oldImagePath != null) {
            Path oldFile = Paths.get(pathName, oldImagePath);
            try {
                Files.deleteIfExists(oldFile); // 없으면 그냥 넘어감
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String imageFileName = "product_" + System.currentTimeMillis() + ".jpg";

        // 2. 새 이미지 저장
        File imageFile = new File(pathName + imageFileName);
        String imageData = updateProduct.getImage(); // Base64 인코딩 문자열

        try {
            // 파일 정보를 byte 단위로 변환하여 이미지를 복사
            // FileOutputStream : byte 파일을 처리해주는 자바의 Stream 클래스
            FileOutputStream fos = new FileOutputStream(imageFile);

            // 메소드 체이닝 : .을 연속적으로 찍어서 메소드를 계속 호출
            byte[] decodedImage = Base64.getDecoder().decode(imageData.split(",")[1]);
            fos.write(decodedImage); // 바이트 파일을 해당 이미지 경로에 복사

            updateProduct.setImage(imageFileName);
            updateProduct.setInputdate(product.getInputdate());
            // updateProduct.setInputdate(LocalDate.now());

            productService.save(updateProduct);

            return ResponseEntity.ok(Map.of("message", "Product update successfully"));
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", err.getMessage(), "error", "Error file uploading"));
        }
    }
}
