package hello.core.singleton;

public class StatefulService {
/*

    private int price;  // 상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; // 여기가 문제!
    }

    public int getPrice() {
        return price;
    }
*/

    // 이렇게 하니까 price를 클라이언트에 의해 값이 변경된다.
    // 아래와 같이 바꿔야 한다.

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;   // 그냥 여기서 price 넘겨버리면 된다.
    }
}
