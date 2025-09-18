package com.coffee.entity;

import com.coffee.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// 회원 1명에 대한 정보를 저장하는 자바 클래스
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity // 해당 클래스를 엔티티로 관리
@Table(name = "members") // 테이블 이름은 "members"로 생성
public class Member {
    @Id // 이 컬럼은 primary key 이다
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 생성 전략
    @Column(name = "member_id") // 컬럼 이름 변경
    private Long id;

    private String name;

    // 필수 사항(null 불가), 중복 불가
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING) // 컬럼에 문자열 형식으로 데이터가 들어감
    private Role role; // 사용자 또는 관리자

    private LocalDate regdate; // 등록 일자
}
