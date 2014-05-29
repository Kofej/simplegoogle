package com.svisoft.common.web;

public abstract class AbstractController
{
  private String getViewPrefix()
  {
    return this.getClass().getCanonicalName()
        .replaceAll("\\.", "/")
        .replaceFirst("^" + "com/svisoft/", "")
        .replaceFirst("/" + this.getClass().getSimpleName() + "$", "");
  }

  public String getView(String viewName)
  {
    return getViewPrefix() + viewName;
  }
}
