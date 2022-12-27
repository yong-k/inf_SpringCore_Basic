package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

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

