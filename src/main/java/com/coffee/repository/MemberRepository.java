package com.coffee.repository;

import com.coffee.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// 회원 정보들을 이용하여 데이터베이스와 교신하는 인터페이스
// 이전의 Dao 역할
// JpaRepository<엔티티이름, 엔티티의기본키변수타입>
public interface MemberRepository extends JpaRepository<Member, Long> {
    // findByEmail를 JPA 에서는 '쿼리 메소드'라 부름
    // 이메일 정보를 이용하여 해당 회원이 존재하는지 체크
    Member findByEmail(String email);
}
