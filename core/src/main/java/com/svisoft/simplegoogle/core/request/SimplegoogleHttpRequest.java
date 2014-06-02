package com.svisoft.simplegoogle.core.request;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;

public class SimplegoogleHttpRequest
{
  private String url;
  private String responseAsString;

  public SimplegoogleHttpRequest(String url)
  {
    if (! url.startsWith("http://"))
      url = "http://" + url;
    if (url.endsWith("/"))
      url = url.substring(0, url.length()-1);

    this.url = url;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public String getResponseAsString()
  {
    return responseAsString;
  }

  public void setResponseAsString(String responseAsString)
  {
    this.responseAsString = responseAsString;
  }

  public SimplegoogleHttpRequest sendRequest()
      throws
      RequestSendException
  {
    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(url);
    try { client.executeMethod(method); }
    catch (Exception e) { throw new RequestSendException(e.getMessage()); }

    String responseAsString;
    try { responseAsString = method.getResponseBodyAsString(); }
    catch (Exception e) { throw new RequestSendException(e.getMessage()); }

    this.responseAsString = responseAsString;

    return this;
  }

  public String getClearText()
  {
    if (responseAsString == null)
      throw new IllegalStateException("Request send exception");

    //TODO:Request: improve to support href attribute (for recursive indexing)
    return Jsoup.parse(responseAsString).text();

    //TODO:RegEx: improve to support situation when javascript code injected into html
    //TODO:RegEx: improve to support href attribute (for recursive indexing)
//    return htmlAsString
//        .replaceAll("\\s", " ")    // delete [ \t\n\x0b\r\f]
//        .replaceAll("<script.*?script>", " ")    // delete [ \t\n\x0b\r\f]
//        .replaceAll("<.*?>", " ")  // delete all html tags like <*></*>
//        .replaceAll("\\n", " ")    // delete all \n
//        .replaceAll(" {2,}", " "); // delete more than one whitespace with one whitespace
  }

  public String getTitle()
  {
    if (responseAsString == null)
      throw new IllegalStateException("Request send exception");

    return Jsoup.parse(responseAsString).title();
  }
}
