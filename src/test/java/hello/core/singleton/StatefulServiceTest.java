package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // 사용자 A가 주문하고 금액을 조회하는 사이에 B의 주문이 끼어든 상황
        // ThreadA: A 사용자가 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB: B 사용자가 20000원 주문
        int userBPrice = statefulService2.order("userB", 20000);

        // ThreadA : 사용자A 주문 금액 조회
        //int price = statefulService1.getPrice();
        //System.out.println("userAPrice = " + userAPrice);     // userAPrice = 20000

        //-- statefulService 1이든 2든 상관없음
        //   들어가보면 인스턴스는 같은 애를 사용하고 있으니까 (싱글톤)

        //Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

        // StatefulService 코드 변경 후, 다시 테스트
        System.out.println("userAPrice = " + userAPrice);   //userAPrice = 10000
        System.out.println("userBPrice = " + userBPrice);   //userBPrice = 20000
    }

    // statefulService 전용으로 config 하나 만듦
    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}