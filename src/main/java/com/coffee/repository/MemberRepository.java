package com.coffee.repository;

import com.coffee.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 회원 정보들을 이용하여 데이터베이스와 교신하는 인터페이스
// 이전의 Dao 역할
// JpaRepository<엔티티이름, 엔티티의기본키변수타입>
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
