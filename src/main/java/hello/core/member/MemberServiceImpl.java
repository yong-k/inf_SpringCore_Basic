package hello.core.member;

public class MemberServiceImpl implements MemberService {

    //private MemberRepository memberRepository = new MemoryMemberRepository();
    //-- DIP 위반
    //   Interface에도 의존하고 있고, 구현Class에도 의존하고 있음


    // [관심사 분리] AppConfig 만든 후,
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //-- DIP 지키고 있음
    //   추상화에만 의존하고 있음

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
