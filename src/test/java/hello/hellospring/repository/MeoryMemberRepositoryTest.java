package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MeoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.claerStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        // 반환타입이 Optional이면 값 꺼낼 때 get 쓸 수 있음. 좋은 방법은 아니나 테스트코드니까
        // Assertions.assertEquals(result, member); // junit꺼
        assertThat(member).isEqualTo(result); // assertj 꺼
        // Assertions 일단 쓰고 static import 하면 assertThat으로 사용가능
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); // shift+F6 변수명바꾸기
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findyByName("spring1").get();
        // .get() 안하면 Optional<Member>임 자료형

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
