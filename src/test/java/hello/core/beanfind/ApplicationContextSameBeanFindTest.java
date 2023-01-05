package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시, 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate() {
        // 현재 타입으로만 빈 조회하는 상황
        //MemberRepository bean = ac.getBean(MemberRepository.class);
        //-- 얘를 호출했을 경우, 예외 발생한다.
        //   실행했을 때, 구성정보로 주어진 SameBeanConfig 살펴본다.
        //   SameBeanConfig에는 memberRepository1, 2만 등록되어 있음
        //   근데 둘 다 타입 MemberRepository라 타입으로 조회하면, 둘 다 튀어나옴
        //   그럼 스프링 입장에서는 '나 뭐 선택해야 하지,,?' 하고 예외 터짐
        //-- 실행해보면 'NoUniqueBeanDefinitionException' 예외 발생한다.

        // 그래서 예외 발생해야 테스트 성공인 걸로 테스트 구문 작성
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회 시, 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);

    }

    // 둘 다 꺼내고 싶으면?
    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);    // 2개인지 확인
    }


    // 중복 테스트 하려면, AppConfig 파일 손대야 하는데, 그러기 싫으니까 여기에 만들거임
    // 테스트 코드이니까 그냥 여기에다가 Config 만들자
    @Configuration
    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

}
