package hello.core.member;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    //MemberService memberService = new MemberServiceImpl();

    // AppConfig 만든 후,
    //--얘는 AppConfig에서 바로 꺼내기 애매해서, @BeforeEach 사용
    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        //given → ~가 주어졌을 때,
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when → ~했을 때,
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then → ~게 된다.
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
