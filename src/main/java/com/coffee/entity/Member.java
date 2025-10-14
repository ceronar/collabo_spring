package com.coffee.entity;

import com.coffee.constant.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    // 필수 사항(null 불가), 중복 불가
    @Column(unique = true, nullable = false)
    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Email
    private String email;

    // @Pattern : 정규 표현식을 의미
    // [asdf] : asdf중 한글자와 매칭
    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 8, max = 255, message = "비밀번호는 8자리 이상, 255자리 이하로 입력해 주세요.")
    @Pattern(regexp = ".*[A-Z].*", message = "비밀 번호는 대문자 1개 이상을 포함해야 합니다.")
    @Pattern(regexp = ".*[!@#$%].*", message = "비밀 번호는 특수 문자 '!@#$%' 중 하나 이상을 포함해야 합니다.")
    private String password;

    @NotBlank(message = "주소는 필수 입력 사항입니다.")
    private String address;

    @Enumerated(EnumType.STRING) // 컬럼에 문자열 형식으로 데이터가 들어감
    private Role role; // 사용자 또는 관리자

    // 자바의 객체를 json 타입으로 변경할 때, LocalDate, LocalDateTime 클래스들이 변환이 원활하지 않음
    // Jackson 라이브러리를 사용하여 이를 해결
    // pom.xml 파일에 Jackson 라이브러리 추가
    @JsonFormat(pattern = "yyyy-MM-dd") // 변환시 날짜 형식을 개발자가 지정
    private LocalDate regdate; // 등록 일자
}
