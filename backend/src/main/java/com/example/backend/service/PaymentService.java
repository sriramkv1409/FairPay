package com.example.backend.service;

import com.example.backend.dto.PaymentLinkResponse;
import com.example.backend.dto.RandomPayResponse;
import com.example.backend.entity.Group;
import com.example.backend.entity.GroupMember;
import com.example.backend.entity.Payment;
import com.example.backend.entity.PaymentStatus;
import com.example.backend.repository.GroupMemberRepository;
import com.example.backend.repository.GroupRepository;
import com.example.backend.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class PaymentService {

    private final GroupRepository groupRepository;
    private final PaymentRepository paymentRepository;
    private final GroupMemberRepository groupMemberRepository;

    public RandomPayResponse randomPay(Long groupId){
          Group group = groupRepository.findById(groupId)
                  .orElseThrow(()->new RuntimeException("Group not found"));

          List<GroupMember> members = groupMemberRepository.findByGroup(group);
          GroupMember selected = members.get(new Random().nextInt(members.size()));

          Payment payment = Payment.builder()
                  .group(group)
                  .user(selected.getUser())
                  .amount(group.getTotalAmount())
                  .build();

          paymentRepository.save(payment);
          return new RandomPayResponse(
                  selected.getUser().getId(),
                  selected.getUser().getName(),
                  group.getTotalAmount()
          );
    }

    public PaymentLinkResponse generateUpiLinks(Long groupId,Double amount){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(()->new RuntimeException("Group not found"));

        String upi=group.getUpiId();
        String base ="upi://pay?pa=" + upi + "&am=" + amount + "&cu=INR";

        return new PaymentLinkResponse(
                base+"&pn=Fairpay-Gpay",
                base+"&pn=Fairpay-PhonePe",
                base+"&pn=Fairpay-Paytm"
        );
    }

    //mark as paid (manual)
    public void markPaid(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()->new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
    }
}
