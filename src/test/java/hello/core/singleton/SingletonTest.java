package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1. 조회: 호출할 때마다 객체를 생성하는 지 조회해보자
        MemberService memberService1 = appConfig.memberService();

        //2. 조회: 호출할 때마다 객체를 생성하는 지 조회해보자
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른 것을 확인해보자
        System.out.println("memberService1 = " + memberService1);
        //--memberService1 = hello.core.member.MemberServiceImpl@66d18979
        System.out.println("memberService2 = " + memberService2);
        //--memberService2 = hello.core.member.MemberServiceImpl@bccb269

        //--> appConfig한테 memberService 달라고 할 때마다 다르게 생성해서 주는 거 확인할 수 있음
        //    효율적이지 않음

        // memberService1 != memberService2 인지 테스트
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);


    }
}
