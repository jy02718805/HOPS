package com.yuecheng.hops.security.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @Title: SecurityRule.java
 * @Package com.yuecheng.hops.identity.entity.security
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年9月1日 下午2:27:27
 * @version V1.0
 * @ClassName: SecurityRule
 */
@Entity
@Table(name = "SECURITY_credential_RULE")
public class SecurityCredentialRule implements Serializable
{
    /**
     * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
     */

    private static final long serialVersionUID = 1136724512950886236L;

    /**
     * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
     */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "securityruleidseq")
    @SequenceGenerator(name = "securityruleidseq", sequenceName = "SECURITY_RULE_ID_SEQ")
    @Column(name = "SECURITY_RULE_ID")
    public Long securityRuleId;

    @Column(name = "SECURITY_RULE_NAME")
    public String securityRuleName;

    @Column(name = "LETTER")
    public String letter; // 字母

    @Column(name = "FIGURE")
    public String figure; // 数字

    @Column(name = "SPECIAL_CHARACTER")
    public String specialCharacter; // 下划线

    @Column(name = "STATUS")
    public Long status;

    @Column(name = "IS_LOWERCASE")
    public String isLowercase; // 大写字母

    @Column(name = "IS_UPPERCASE")
    public String isUpperCase; // 小写字母

    @Override
    public String toString()
    {
        return "SecurityRule:[" + "securityruleid=" + securityRuleId + " ; securityrulename="
               + securityRuleName + " ; letter=" + letter + " ; figure=" + figure
               + " ; specialcharacter=" + specialCharacter + " ; status=" + status
               + " ; islowercase=" + isLowercase + " ; isupperCase=" + isUpperCase + "]";

    }

    /**
     * getter method
     * 
     * @return the securityruleid
     */

    public Long getSecurityRuleId()
    {
        return securityRuleId;
    }

    /**
     * setter method
     * 
     * @param securityruleid
     *            the securityruleid to set
     */

    public void setSecurityRuleId(Long securityRuleId)
    {
        this.securityRuleId = securityRuleId;
    }

    /**
     * getter method
     * 
     * @return the securityrulename
     */

    public String getSecurityRuleName()
    {
        return securityRuleName;
    }

    /**
     * setter method
     * 
     * @param securityrulename
     *            the securityrulename to set
     */

    public void setSecurityRuleName(String securityRuleName)
    {
        this.securityRuleName = securityRuleName;
    }

    /**
     * getter method
     * 
     * @return the letter
     */

    public String getLetter()
    {
        return letter;
    }

    /**
     * setter method
     * 
     * @param letter
     *            the letter to set
     */

    public void setLetter(String letter)
    {
        this.letter = letter;
    }

    /**
     * getter method
     * 
     * @return the figure
     */

    public String getFigure()
    {
        return figure;
    }

    /**
     * setter method
     * 
     * @param figure
     *            the figure to set
     */

    public void setFigure(String figure)
    {
        this.figure = figure;
    }

    /**
     * getter method
     * 
     * @return the specialcharacter
     */

    public String getSpecialCharacter()
    {
        return specialCharacter;
    }

    /**
     * setter method
     * 
     * @param specialcharacter
     *            the specialcharacter to set
     */

    public void setSpecialCharacter(String specialCharacter)
    {
        this.specialCharacter = specialCharacter;
    }

    /**
     * getter method
     * 
     * @return the status
     */

    public Long getStatus()
    {
        return status;
    }

    /**
     * setter method
     * 
     * @param status
     *            the status to set
     */

    public void setStatus(Long status)
    {
        this.status = status;
    }

    /**
     * getter method
     * 
     * @return the islowercase
     */

    public String getIsLowercase()
    {
        return isLowercase;
    }

    /**
     * setter method
     * 
     * @param islowercase
     *            the islowercase to set
     */

    public void setIsLowercase(String isLowercase)
    {
        this.isLowercase = isLowercase;
    }

    /**
     * getter method
     * 
     * @return the isupperCase
     */

    public String getIsUpperCase()
    {
        return isUpperCase;
    }

    /**
     * setter method
     * 
     * @param isupperCase
     *            the isupperCase to set
     */

    public void setIsUpperCase(String isUpperCase)
    {
        this.isUpperCase = isUpperCase;
    }

}
