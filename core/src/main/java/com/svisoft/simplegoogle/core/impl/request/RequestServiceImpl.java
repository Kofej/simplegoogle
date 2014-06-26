package com.svisoft.simplegoogle.core.impl.request;

import com.svisoft.simplegoogle.core.request.RequestSendException;
import com.svisoft.simplegoogle.core.request.RequestService;
import org.jsoup.Jsoup;

import static com.google.common.base.Preconditions.checkNotNull;

public class RequestServiceImpl
    implements RequestService
{
  @Override
  public String getHtmlAsStringByUrl(String url)
      throws
      RequestSendException
  {
    checkNotNull(url);

    try
    {
      String userAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:30.0) Gecko/20100101 Firefox/30.0"; // optional
      return Jsoup.connect(url).userAgent(userAgent).get().outerHtml();
    }
    catch(Exception e)
    {
      throw new RequestSendException(e.getMessage());
    }
  }
}
