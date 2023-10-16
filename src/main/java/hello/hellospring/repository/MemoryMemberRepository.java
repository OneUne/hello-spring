package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    // 실무에서는 공유되는 변수는 동시성 문제로 ConcurrentHashMap 사용해야 함
    // Map<키, 값> 형태
    private static long sequence = 0L; // 얘도 실무일 경우 AtomicLong. 동시성 문제.

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null이 반환될 수 있는 경우 Optional로 감싸줘야 함
    }

    @Override
    public Optional<Member> findyByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        // 얘는 왜 안 감싸주나요? findAny()가 없으면 Optional.empty() 반환함
        /*
        Stream을 직렬로 처리할 때는 findFirst()나 findAny()가 동일한 요소 리턴
        병렬로 처리하면 findFirst()는 순서 고려, findAny()는 안 고려.
         */
     }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 실무에서도 List 많이 씁니다.
    }

    public void claerStore(){
        store.clear();
    }
}
