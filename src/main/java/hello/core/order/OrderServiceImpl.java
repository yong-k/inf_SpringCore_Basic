package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //-- interface와 구현class 모두에 의존하고 있는 상태
    private DiscountPolicy discountPolicy;
    //-- 이제 interface에만 의존하고 있음. 근데 구현체가 없으로 NullPointException 발생
    //-- 해결하려면,
    //   누군가가 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해줘야 함

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        //-- 설계 잘 된 것이다.
        //   OrderService 입장에서는,
        //   할인에 대해서 나는 모르겠음. 할인에 대한 건 discountPolicy 너가 알아서 해 줘.
        //   너가 알아서 하고 나한테 결과만 던져줘.
        //--> 단일 책임의 원칙(SRP)을 잘 지킨 것
        //    (할인 변경되면, 할인 쪽만 고치면 되니까)

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}

