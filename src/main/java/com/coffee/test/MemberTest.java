package com.coffee.test;

import com.coffee.constant.Role;
import com.coffee.entity.Member;
import com.coffee.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootTest // 이 클래스는 간단한 테스트를 위한 용도로 사용
public class MemberTest {
    @Autowired // 의존하고 있는 객체를 외부에서 자동 주입
    private MemberRepository memRepo; // 기본값 null

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 몇명 추가")
    public void insertMemberList() {
        // 회원 몇 명 추가
        Member mem01 = new Member();
        mem01.setName("관리자");
        mem01.setEmail("admin@naver.com");
        mem01.setPassword(passwordEncoder.encode("Admin@123"));
        mem01.setAddress("마포구 공덕동");
        mem01.setRole(Role.ADMIN);
        mem01.setRegdate(LocalDate.now());

        memRepo.save(mem01); // 데이터베이스에 insert
        System.out.println("----------------");

        Member mem02 = new Member();
        mem02.setName("유영석");
        mem02.setEmail("bluesky@naver.com");
        mem02.setPassword(passwordEncoder.encode("Bluesky@456"));
        mem02.setAddress("용산구 이태원동");
        mem02.setRole(Role.USER);
        mem02.setRegdate(LocalDate.now());
        memRepo.save(mem02) ;
        System.out.println("----------------------");

        Member mem03 = new Member();
        mem03.setName("곰돌이");
        mem03.setEmail("gomdori@naver.com");
        mem03.setPassword(passwordEncoder.encode("Gomdori@789"));
        mem03.setAddress("동대문구 휘경동");
        mem03.setRole(Role.USER);
        mem03.setRegdate(LocalDate.now());
        memRepo.save(mem03) ;
        System.out.println("----------------------");
    }
}
