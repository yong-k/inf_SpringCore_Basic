package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

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
        System.out.println("memberService2 = " + memberService2);
        //--memberService1 = hello.core.member.MemberServiceImpl@66d18979
        //  memberService2 = hello.core.member.MemberServiceImpl@bccb269

        //--> appConfig한테 memberService 달라고 할 때마다 다르게 생성해서 주는 거 확인할 수 있음
        //    효율적이지 않음

        // memberService1 != memberService2 인지 테스트
        assertThat(memberService1).isNotSameAs(memberService2);

    }

/*
    public static void main(String[] args) {
        SingletonService singletonService1 = new SingletonService();
        SingletonService singletonService2 = new SingletonService();
        // 내가 아무리 내부에 감추려고 해도, 누군가가 SingletonService를 new로 만들어버릴 수 있음
        //--→ 그래서 private 생성자를 쓴다. 의외로 private 생성자 자주 사용한다.
        //    생성을 막아버린다.

        // SingletonService에 private 생성자 만들고 와서 다시 하면 빨간 줄 뜬다.
        // (private 이라 접근 못한다고)
        SingletonService singletonService = new SingletonService();
    }
*/

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        //new SingletonService();
        //--컴파일 오류 발생(SingletonService() has private access)

        // 호출할 때마다 객체를 생성하는 지 조회해보자
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        //--singletonService1 = hello.core.singleton.SingletonService@63475ace
        //  singletonService2 = hello.core.singleton.SingletonService@63475ace

        //--→ 같은 객체 인스턴스가 반환되었다.
        // 예시로 객체 인스턴스를 생성하는 데 드는 비용이 1000 정도라면,
        // 참조로 가져오는 비용은 1 정도라고 생각하면 된다.

        assertThat(singletonService1).isSameAs(singletonService2);
        //same : == 비교처럼 참조를 비교하는 것
        //equal: equals() 처럼 비교하는 것
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        // 조회할 때마다 같은 객체 반환하는 지 확인
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        //memberService1 = hello.core.member.MemberServiceImpl@73173f63
        //memberService2 = hello.core.member.MemberServiceImpl@73173f63

        assertThat(memberService1).isSameAs(memberService2);
    }
}
