package com.coffee.constant;

// 상품의 카테고리 정보 열거형 상수
// 한글 이름도 같이 명시
public enum Category {
    BREAD("빵"), BEVERAGE("음료수"), CAKE("케이크");

    private String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
