package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        //System.out.println("memberService = " + memberService);
        //System.out.println("memberService.getClass() = " + memberService.getClass());

        // memberService가 MemberServiceImpl의 인스턴스 객체인지 테스트
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    //-- 지금은 MemberSerivce 인터페이스로 조회했음
    //   인터페이스로 조회하면, 인터페이스의 구현체가 대상이 된다.

    // 구체 타입으로 조회해보자 (스프링 컨테이너에 해당 타입 등록되어 있으면, 그냥 조회됨)
    //-- 좋은 코드는 아님 (구현체가 아니라 인터페이스에 의존하고 있어야 좋음)
    //   구체 타입으로 조회할 경우 프로그램 유연성 떨어짐
    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
        //ac.getBean("xxxxx", MemberService.class);

        //MemberService xxxxx = ac.getBean("xxxxx", MemberService.class);
        //-- xxxxx 라는 이름의 빈은 없는데, 조회해보면, 예외 발생한다.
        //   (NoSuchBeanDefinitionException: No bean named 'xxxxx' available)

        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxx", MemberService.class));
        //-- 오른쪽에 있는 로직을 실행했을 때
        //   NoSuchBeanDefinitionException 예외가 무조건 발생해야, 테스트가 성공하는 것임
        //   해당 예외 발생하지 않으면 테스트 실패
    }
}
