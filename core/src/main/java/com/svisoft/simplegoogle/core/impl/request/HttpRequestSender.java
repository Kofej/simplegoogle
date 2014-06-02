package com.svisoft.simplegoogle.core.impl.request;

import com.svisoft.simplegoogle.core.request.RequestSendException;
import com.svisoft.simplegoogle.core.request.RequestSender;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;

public class HttpRequestSender
    implements RequestSender
{
  private String url;

  public HttpRequestSender(String url)
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

  @Override
  public String send()
      throws
      RequestSendException
  {
    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(url);
    try { client.executeMethod(method); }
    catch (Exception e) { throw new RequestSendException(e.getMessage()); }

    String requestAsString;
    try { requestAsString = method.getResponseBodyAsString(); }
    catch (Exception e) { throw new RequestSendException(e.getMessage()); }

    return extractClearTextFromStringifiedHtml(requestAsString);
  }

  private String extractClearTextFromStringifiedHtml(String htmlAsString)
  {
    //TODO:Request: improve to support href attribute (for recursive indexing)
    return Jsoup.parse(htmlAsString).text();

    //TODO:RegEx: improve to support situation when javascript code injected into html
    //TODO:RegEx: improve to support href attribute (for recursive indexing)
//    return htmlAsString
//        .replaceAll("\\s", " ")    // delete [ \t\n\x0b\r\f]
//        .replaceAll("<script.*?script>", " ")    // delete [ \t\n\x0b\r\f]
//        .replaceAll("<.*?>", " ")  // delete all html tags like <*></*>
//        .replaceAll("\\n", " ")    // delete all \n
//        .replaceAll(" {2,}", " "); // delete more than one whitespace with one whitespace
  }

}
