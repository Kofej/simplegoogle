package com.svisoft.simplegoogle.core.impl.initialize;

import com.svisoft.simplegoogle.core.initialize.InitializeService;
import com.svisoft.simplegoogle.core.request.HtmlParser;
import com.svisoft.simplegoogle.core.request.RequestService;
import com.svisoft.simplegoogle.core.storage.StorageService;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class InitializeServiceImpl
    implements InitializeService
{
  private StorageService storageService;
  private RequestService requestService;

  public void setStorageService(StorageService storageService)
  {
    this.storageService = storageService;
  }

  public void setRequestService(RequestService requestService)
  {
    this.requestService = requestService;
  }

  @Override
  public void initByUrl(String url, Integer depth)
  {
    checkNotNull(depth);

    deepInitialize(url, depth, new HashSet<String>());
  }

  private void deepInitialize(String url, Integer depth, Set<String> collector)
  {
    if (depth <= 0)
      return;
    --depth;

    try
    {
      HtmlParser parser = new HtmlParser(requestService.getHtmlAsStringByUrl(url), url);

      storageService.updateOrCreate(url, parser.getTitleFromHtml(), parser.getClearTextFromHtml());
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
      e.printStackTrace(); // just skip exceptions...
    }
  }
}
