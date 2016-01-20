package com.yuecheng.hops.common.persistence;

import java.io.Serializable;

public class SelectLabel implements Serializable
{
	private static final long serialVersionUID = 4253343184352175139L;
	private String text;
	private String id;
	private boolean selected;

	public SelectLabel()
	{

	}

	public SelectLabel(String text, String id)
	{
		this.text = text;
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

}
