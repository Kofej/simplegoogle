package com.svisoft.simplegoogle.core.impl.initialize;

import com.svisoft.simplegoogle.core.initialize.InitializeService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class InitializeServiceImpl
    implements InitializeService
{
  @Override
  public void initByUrl(String url)
      throws
      Exception
  {
    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(url);
    try
    {
      client.executeMethod(method);
    }
    catch (Exception e)
    {
      return;
    }

    initStringifiedHtmlPage(url, method.getResponseBodyAsString());
  }

  @Override
  public void initStringifiedHtmlPage(String url, String htmlPageAsString)
      throws
      Exception
  {
    // TODO:Lucene: indexing here. Currently just simple output
    System.out.println(extractClearTextFromStringifiedHtml(htmlPageAsString));
  }

  @Override
  public String extractClearTextFromStringifiedHtml(String htmlAsString)
  {
    //TODO:RegEx: improve to support situation when javascript code injected into html
    //TODO:RegEx: improve to support href attribute (for recursive indexing)
    return htmlAsString
        .replaceAll("\\s", " ")    // delete [ \t\n\x0b\r\f]
        .replaceAll("<.*?>", " ")  // delete all html tags like <*></*>
        .replaceAll("\\n", " ")    // delete all \n
        .replaceAll(" {2,}", " "); // delete more than one whitespace with one whitespace
  }
}
