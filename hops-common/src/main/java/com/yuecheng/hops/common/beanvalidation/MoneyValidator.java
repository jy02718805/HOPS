package com.yuecheng.hops.common.beanvalidation;


import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MoneyValidator implements ConstraintValidator<Money, BigDecimal>
{

    @Override
    public void initialize(Money money)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
