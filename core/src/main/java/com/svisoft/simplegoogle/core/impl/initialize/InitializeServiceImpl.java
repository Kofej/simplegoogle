package com.svisoft.simplegoogle.core.impl.initialize;

import com.svisoft.simplegoogle.core.initialize.InitializeService;
import com.svisoft.simplegoogle.core.request.HtmlParser;
import com.svisoft.simplegoogle.core.request.RequestService;
import com.svisoft.simplegoogle.core.storage.IndexService;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class InitializeServiceImpl
    implements InitializeService
{
  private IndexService indexService;
  private RequestService requestService;

  public void setIndexService(IndexService indexService)
  {
    this.indexService = indexService;
  }

  public void setRequestService(RequestService requestService)
  {
    this.requestService = requestService;
  }

  @Override
  public void initByUrl(String url, Integer depth)
  {
    checkNotNull(depth);

    System.out.println("Start initialize process for url: \"" + url + "\" with depth=" + depth + ".");
    HashSet<String> collector =  new HashSet<String>();
    deepInitialize(url, depth, collector);
    System.out.println("Initialization process finished. Created/updated count: " + collector.size() + ".");
  }

  private void deepInitialize(String url, Integer depth, Set<String> collector)
  {
    if (depth <= 0)
      return;
    --depth;

    try
    {
      HtmlParser parser = new HtmlParser(requestService.getHtmlAsStringByUrl(url), url);

      indexService.index(url, parser.getTitleFromHtml(), parser.getClearTextFromHtml());
      System.out.println(url + " : success.");
      collector.add(url);

      if (depth > 0)
      {
        Set<String> urls = parser.getAbsUrls();
        for (String u : urls)
        {
          if (collector.contains(u)) continue;
          deepInitialize(u, depth, collector);
        }
      }
    }
    catch (Exception e)
    {
      System.err.println(url + " : error. " + e.getMessage());
    }
  }
}
