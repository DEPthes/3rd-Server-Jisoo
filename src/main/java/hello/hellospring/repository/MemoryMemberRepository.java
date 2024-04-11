package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    //임시 DB
    private static Map<Long, Member> store = new HashMap<>();
    //시스템이 임의로 아이디를 지정해 저장하기 위해 sequence변수 사용
    private static long sequence = 0L;
    //회원이 저장소에 저장
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //store.get(id)의 값이 null인 경우 Optional.empty()가 반환
    }
    //store.values() 를 통해
    // store 에 저장된 value값들만 가져오고,
    // 이를 stream().filter() 로 파라미터로 받아온 name 과 일치하는 멤버를 찾아 반환
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }
    //지금까지 저장된 모든 리스트 반환
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
