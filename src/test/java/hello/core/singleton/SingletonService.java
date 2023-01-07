package hello.core.singleton;

public class SingletonService {

    // 자기 자신을 내부에 하나 가지고 있는데, private static으로.
    // 이렇게 하면, 클래스 레벨에 존재하기 때문에 딱 하나만 존재한다.
    private static final SingletonService instance = new SingletonService();
    //--→ 이렇게 하면, 자바가 뜰 때, SingletonService의 static 영역에 옆에 new라고 되어있는 거
    //    내부적으로 실행해서, 객체를 생성한 다음에 인스턴스의 참조에 넣어둔다.

    // 조회할 수 있도록
    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자 사용해서 외부에서 객체 생성 못하도록 막음
    private SingletonService() {
    }
    //--SingletonTest에 main 메서드 만들고 new로 SingletonSerivce() 생성해보자
    //  여기다가 하면 내부에서는 private 사용할 수 있으니까 no

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

    //-- 완벽한 싱글톤 패턴 완성
    //   자바가 뜨면서, static 영역에 있는 instance를 new로 한 번 생성해서 가지고 있고,
    //   저 instance의 참조를 꺼낼 수 있는 방법은 getInstance() 밖에 없음
    //   instance를 생성할 수 있는 건 아무 것도 없다.
}
