package hello.core.order;

public interface OrderService {
    // 최종 주문 결과 반환
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
