package com.yuecheng.hops.common.beanvalidation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.util.StringUtils;


public class ForbiddenValidator implements ConstraintValidator<Forbidden, String>
{

    private String[] forbiddenWords = {"admin"};

    @Override
    public void initialize(Forbidden constraintAnnotation)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        if (StringUtils.isEmpty(value))
        {
            return true;
        }

        for (String word : forbiddenWords)
        {
            if (value.contains(word))
            {
                ((ConstraintValidatorContextImpl)context).getConstraintDescriptor().getAttributes().put(
                    "word", word);
                return false;
            }
        }
        return true;
    }

}
