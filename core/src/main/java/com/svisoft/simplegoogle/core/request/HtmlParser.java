package com.svisoft.simplegoogle.core.request;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashSet;
import java.util.Set;

public class HtmlParser
{
  private Document document;

  public HtmlParser(String html, String baseUrl)
  {
    // Looks like Jsoup lib incorrect applies relative url to base, in case if base url does not ends up with "/".
    // The end slash does not affect anyhow on request. It's just to correct Jsoup url making process.
    if (! baseUrl.endsWith("/"))
      baseUrl = baseUrl + "/";
    document = Jsoup.parse(html, baseUrl);
  }

  public Set<String> getAbsUrls()
  {
    Set<String> result = new HashSet<String>();
    for (Element e : document.select("a[href]"))
      result.add(e.attr("abs:href"));

    return result;
  }


  public String getTitleFromHtml()
  {
    return document.title();
  }

  public String getClearTextFromHtml()
  {
    return document.text();
  }
}
