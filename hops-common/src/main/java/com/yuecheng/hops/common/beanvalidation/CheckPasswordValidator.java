package com.yuecheng.hops.common.beanvalidation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.yuecheng.hops.common.Constant.SecurityCredential;


public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, SecurityCredential>
{

    @Override
    public void initialize(CheckPassword arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isValid(SecurityCredential securityCredential,
                           ConstraintValidatorContext context)
    {
        if (securityCredential == null)
        {
            return true;
        }

        // 没有填密码
        if (!StringUtils.hasText(securityCredential.toString()))
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{password.null}").addPropertyNode(
                "password").addConstraintViolation();
            return false;
        }

        if (!StringUtils.hasText(securityCredential.toString()))
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{password.confirmation.null}").addPropertyNode(
                "confirmation").addConstraintViolation();
            return false;
        }

        // 两次密码不一样
        if (!securityCredential.toString().trim().equals(securityCredential.toString()))
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{password.confirmation.error}").addPropertyNode(
                "confirmation").addConstraintViolation();
            return false;
        }
        return true;
    }
}
