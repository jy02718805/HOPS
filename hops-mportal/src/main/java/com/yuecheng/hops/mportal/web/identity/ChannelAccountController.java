package com.yuecheng.hops.mportal.web.identity;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/Channel")
public class ChannelAccountController
{

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addChannel()
    {
        return "admin/channel_add.ftl";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listChannel()
    {
        return "admin/channel_list.ftl";
    }
}
