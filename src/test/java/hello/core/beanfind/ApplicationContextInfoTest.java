package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            // 타입 지정 안 했기 때문에 Object로 꺼내진다.
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            // bean 하나하나에 대한 메타데이터 정보
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            // ROLE 크게 3개 있는데, 주로 ROLE_APPLICATION, ROLE_INFRASTRUCTURE를 사용한다.
            // ROLE.SUPPORT는 잘 사용하지 않음

            // Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
            //-- 스프링이 내부에서 뭘 하기 위해 등록한 거 말고,
            //   내가 애플리케이션 주로 개발하기 위해서 등록한 빈
            // Role ROLE_INFRASTRUCTURE: 스프링이 스프링 컨테이너 내부에서 사용하는 빈

            //-- 이 경우에만 출력하도록 할 거임
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
        }
    }
}
