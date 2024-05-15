package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach //각각의 단위 테스드 메소드 실행 이후에 실행됨
    // 주로 단위 테스트 중 사용되었던 리소스를 처리하는 용도로 사용된다.
    public void afterEach(){
        repository.clearStore();
    }
    @Test //해당 메소드가 단위 테스트 메소드임을 저장
    public void save(){
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        repository.save(member);

        //then
        //findById() 로 저장된 회원객체를 다시 가져와,
        // 저장한 회원객체/불러온 회원객체가 서로 일치하는지 확인함
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);

    }
    @Test
    public void findByName(){
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
       Member result = repository.findByName("spring1").get();

       //then
       assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

}
