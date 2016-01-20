package com.yuecheng.hops.aportal.web.identity;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/recharge")
public class HtmlController
{
    @RequestMapping(value="userinput",method = RequestMethod.GET)
    public String userinput(ModelMap model)
    {
         return "transaction/order/input.ftl";
    }
    
    @RequestMapping(value="userresult",method = RequestMethod.GET)
    public String userresult(ModelMap model)
    {
         return "transaction/order/result.ftl";
    }
    
    @RequestMapping(value="usercomfim",method = RequestMethod.GET)
    public String usercomfim(ModelMap model)
    {
         return "transaction/order/comfim.ftl";
    }
    
    @RequestMapping(value="accountrecharge",method = RequestMethod.GET)
    public String accountrecharge(ModelMap model)
    {
         return "account/accountrecharge.ftl";
    }
}
