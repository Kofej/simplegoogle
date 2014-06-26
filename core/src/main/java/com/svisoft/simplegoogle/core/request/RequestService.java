package com.svisoft.simplegoogle.core.request;

public interface RequestService
{
  String getHtmlAsStringByUrl(String url)
      throws
      RequestSendException;
}
