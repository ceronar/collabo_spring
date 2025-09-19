package com.coffee.service;

import com.coffee.constant.Role;
import com.coffee.entity.Member;
import com.coffee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service // 서비스 역할. 주로 로직 처리에 활용되는 자바 클래스
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memRepo;

    public Member findByEmail(String email) {
        return memRepo.findByEmail(email);
    }

    public void insert(Member bean) {
        // 사용자 '역할'과 '등록 일자'는 여기서 넣음
        bean.setRole(Role.USER);
        bean.setRegdate(LocalDate.now());

        // 주의) Repository에서 insert 작업은 save() 메소드 사용
        memRepo.save(bean);
    }
}
