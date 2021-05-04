package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/*
    @PersistenceContext // jpa 표준api
   public MemberRepository(EntityManger em){ //영속성 컨텍스트에 멤버 엔티티를 넣고 트렌젝션이 커밋되는 시점에 DB에 INSERT 쿼리가 날아가는거
        this.em = em; // member을 집어넣으면 jpa가 저장해주는 로직
    } RequiredArgsConstructor 이게 이걸 생략시켜준다.

*/

@Repository //컴포넌트스캔으로 자동으로 스프링 빈으로 관리 된다
@RequiredArgsConstructor
public class MemberRepository {


    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){ // 단건조회
        return em.find(Member.class,id); // 멤버를 찾아서 반환해주는 역할
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class) // 첫번째 jpql, 두번째 반환타입 얘는 Entity 객체를 대상으로 쿼리조회
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
