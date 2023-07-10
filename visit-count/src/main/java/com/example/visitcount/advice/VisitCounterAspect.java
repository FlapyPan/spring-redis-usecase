package com.example.visitcount.advice;

import com.example.visitcount.service.VisitCountService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class VisitCounterAspect {

    private final VisitCountService visitCountService;

    public VisitCounterAspect(VisitCountService visitCountService) {
        this.visitCountService = visitCountService;
    }

    @Before("@annotation(com.example.visitcount.advice.VisitCounter) || " +
            "@within(com.example.visitcount.advice.VisitCounter)")
    public void counter() {
        visitCountService.incr();
    }

}
