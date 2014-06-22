package com.svisoft.simplegoogle.core.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class SimplegoogleHttpRequest
{
  private String url;
  private Document parsedResponse;

  public SimplegoogleHttpRequest(String url)
  {
    if (! url.startsWith("http://"))
      url = "http://" + url;
    if (! url.endsWith("/"))
      url = url + "/";

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

  public Document getParsedResponse()
  {
    return parsedResponse;
  }

  public void setParsedResponse(Document parsedResponse)
  {
    this.parsedResponse = parsedResponse;
  }

  public SimplegoogleHttpRequest execute()
      throws
      RequestSendException
  {
    try
    {
      String ua = "Mozilla/5.0 (X11; Linux x86_64; rv:30.0) Gecko/20100101 Firefox/30.0";
//      Map<String, String> cookies = Jsoup.connect(url).execute().cookies();
      parsedResponse = Jsoup.connect(url).userAgent(ua).get();
    }
    catch(Exception e)
    {
      throw new RequestSendException(e.getMessage());
    }

    return this;
  }

  // Utilz

  public String getClearText()
  {
    if (parsedResponse == null)
      throw new IllegalStateException("Response is missed");

    return parsedResponse.text();
  }

  public String getTitle()
  {
    if (parsedResponse == null)
      throw new IllegalStateException("Response is missed");

    return parsedResponse.title().isEmpty() ? "no title" : parsedResponse.title();
  }

  public static void deepScan(Set<SimplegoogleHttpRequest> requests, int depth, Set<SimplegoogleHttpRequest> collector)
  {
    if (depth <= 0 || requests.size() == 0)
      return;

    Set<SimplegoogleHttpRequest> toDeepScanList = new HashSet<SimplegoogleHttpRequest>();
    for (SimplegoogleHttpRequest req : requests)
    {
      if (collector.contains(req)) continue;

      try
      {
        Elements elements = req.execute().getParsedResponse().select("a[href]");
        collector.add(req);
        for(Element e : elements)
        {
          toDeepScanList.add(new SimplegoogleHttpRequest(e.attr("abs:href")));
        }

      } catch (Exception ignored) {}
    }

    deepScan(toDeepScanList, depth - 1, collector);
  }

  // Object
  @Override
  public boolean equals(Object obj)
  {
    if (null == obj)
      return false;
    if (this == obj)
      return true;
    if (!(obj instanceof SimplegoogleHttpRequest))
      return false;
    final SimplegoogleHttpRequest lhs = this;
    final SimplegoogleHttpRequest rhs = (SimplegoogleHttpRequest) obj;

    if (null == lhs.getUrl() || null == rhs.getUrl())
      return false;

    return new EqualsBuilder()
        .append(lhs.getUrl(), rhs.getUrl())
        .isEquals();
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder()
        .append(getUrl())
        .toHashCode();
  }
}
