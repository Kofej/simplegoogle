package com.svisoft.simplegoogle.core.storage;

public class SimplegoogleDocument
{
  private String id;
  private String title;
  private String context;
  private String text;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getContext()
  {
    return context;
  }

  public void setContext(String context)
  {
    this.context = context;
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }
}
