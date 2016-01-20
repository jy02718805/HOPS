package com.yuecheng.hops.common.query;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class YcPage<T> implements Serializable
{

    private static final long serialVersionUID = 3879643657654079407L;

    public List<T> list = new ArrayList<T>();

    public int pageTotal;

    public int countTotal;

    public YcPage()
    {}

    public YcPage(List<T> list, int pageTotal, int countTotal)
    {
        this.list = list;
        this.pageTotal = pageTotal;
        this.countTotal = countTotal;
    }

    public List<T> getList()
    {
        return list;
    }

    public void setList(List<T> list)
    {
        this.list = list;
    }

    public int getPageTotal()
    {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal)
    {
        this.pageTotal = pageTotal;
    }

    public int getCountTotal()
    {
        return countTotal;
    }

    public void setCountTotal(int countTotal)
    {
        this.countTotal = countTotal;
    }
}
