package com.raymundo.bankapp.utils;

import com.raymundo.bankapp.dto.CreditDto;
import com.raymundo.bankapp.dto.PaymentScheduleDto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calculator {

    private double creditSum;
    private final double interestRate;
    private final double loanRepaymentAmount;
    private final Calendar date;

    public Calculator(CreditDto credit, double creditSum) {
        this.interestRate = credit.getInterestRate();
        this.creditSum = creditSum;
        this.date = Calendar.getInstance();
        loanRepaymentAmount = creditSum / 12;
        date.add(Calendar.MONTH, 1);
    }

    private PaymentScheduleDto getSchedule() {
        double interestPaymentAmount = (creditSum * interestRate / 100) / 12;
        double paymentAmount = loanRepaymentAmount + interestPaymentAmount;
        creditSum -= loanRepaymentAmount;
        PaymentScheduleDto schedule = new PaymentScheduleDto(date.getTime(), paymentAmount, loanRepaymentAmount, interestPaymentAmount);
        date.add(Calendar.MONTH, 1);
        return schedule;
    }

    public List<PaymentScheduleDto> getSchedules() {
        List<PaymentScheduleDto> result = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            result.add(getSchedule());
        return result;
    }

}
