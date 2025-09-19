package com.coffee.controller;

import com.coffee.entity.Member;
import com.coffee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 해당 클래스는 회원과 관련된 웹 요청(from react)을 접수하여 처리해주는 컨트롤러 클래스
@RequiredArgsConstructor // final 키워드 또는 @NotNull 필드가 들어있는 식별자에 생성자를 통하여 값을 외부에서 주입
public class MemberController {
    private final MemberService mService;

    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody Member bean) { // 회원가입을 위한 컨트롤러 메소드
        // ResponseEntity : Http 응답 코드(숫자 형식) or 적절한 메세지 등을 표현하기 위한 클래스
        // JSON : JavaScript Object Notation 자바스크립트 객체 표기 방식
        // @RequestBody : JSON 형태의 문자열을 자바의 객체 타입으로 변환해줌
        System.out.println(bean);

        // 입력된 이메일을 이용하여 이메일 중복 체크
        Member member = mService.findByEmail(bean.getEmail());

        if (member != null) { // 이미 존재하는 email
            return new ResponseEntity<>(Map.of("email", "이미 존재하는 이메일 주소입니다."), HttpStatus.BAD_REQUEST);
        } else { // 회원 가입 처리
            mService.insert(bean);
            return new ResponseEntity<>("회원 가입 성공", HttpStatus.OK);
        }
    }
}
