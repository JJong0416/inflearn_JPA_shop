package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


// 데이터를 변경하는건 무조건 트랜젝션 안에 있어야 하므로 변경하는 서비스에는 레퍼지토리에 트랜젝션 어노테이션 선언
// 기본적으로 public 메서드는 트렌잭션 안에 걸려 들어간다.


/*
    @Autowired // Construct Injection 생성자 주입에 장점은 생성자가 하나 있으면 AutoWired 안써도 된다
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } @RequiredArgsConstructor은 이걸 생략하게 해준다.
*/
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 이건 final 달려있는 것만 생성자를 만들어준다
public class MemberService {


    private final MemberRepository memberRepository;


    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId(); // 이렇게 꺼내면 항상 값이 있다고 보장받을 수 있다.
    }


    private void validateDuplicateMember(Member member) { // 중복회원 검증
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    // 회원 전체 조회
    //@Transactional(readOnly = true)  조회하는 부근에서 readOnly = true로 주면 JPA 가 성능을 최적화 시켜준다.
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    // 회원 단일 조회
    //@Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
