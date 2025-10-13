package com.coffee.dto;

import com.coffee.constant.Category;
import lombok.*;

// 상품에 대한 필드 검색시 사용하는 클래스
@Getter @Setter @ToString
@AllArgsConstructor // 인스턴스 변수 모두를 매개 변수로 하는 생성자 생성
public class SearchDto {
    // 조회할 날짜 검색 범위를 선정하기 위한 변수, 현재 시간과 상품 입고일을 비교하여 처리
    // all(전체기간), 1d(하루), 1w(일주일), 1m(한달), 6m(여섯달)
    private String searchDateType; // 기간 검색 콤보 박스

    private Category category; // 검색하고자 하는 특정 카테고리 콤보 박스

    // 상품 검색 모드 콤보 박스 | 상품이름(name) or 상품설명(description)
    private String searchMode;

    private String searchKeyword; // 검색 키워드 입력 상자
}
