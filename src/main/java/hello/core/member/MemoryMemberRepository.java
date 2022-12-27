package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {

    // 실무에서는 동시성 문제 때문에 ConcurrentHashMap 사용하지만,
    // 예제에서는 이거까지 설명하면 너무 길어지므로 그냥 HashMap 사용할 예정
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
