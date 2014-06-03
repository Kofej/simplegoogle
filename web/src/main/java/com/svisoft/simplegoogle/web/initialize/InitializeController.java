package com.svisoft.simplegoogle.web.initialize;

import com.google.common.collect.Sets;
import com.svisoft.common.web.AbstractController;
import com.svisoft.simplegoogle.core.request.SimplegoogleHttpRequest;
import com.svisoft.simplegoogle.core.storage.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(InitializeUrl.PREFIX)
public class InitializeController
    extends AbstractController
{
  private StorageService storageService;

  public void setStorageService(StorageService storageService)
  {
    this.storageService = storageService;
  }

  @RequestMapping(value = InitializeUrl.INDEX)
  public String initializeByUrl(
      @RequestParam(defaultValue = "") String url,
      @RequestParam(defaultValue = "5") Integer depth,
      Model model,
      WebRequest request,
      HttpSession session
  )
      throws
      Exception
  {
//    SimplegoogleHttpRequest httpRequest = new SimplegoogleHttpRequest(uri);
//    storageService.updateOrCreate(httpRequest.getUrl(), httpRequest.getTitle(), httpRequest.getClearText());
    if (! url.equals(""))
    {
      String urlToScan = url;
      Set<SimplegoogleHttpRequest> collector = new HashSet<SimplegoogleHttpRequest>();
      SimplegoogleHttpRequest.deepScan(Sets.newHashSet(new SimplegoogleHttpRequest(urlToScan)), depth, collector);
      for (SimplegoogleHttpRequest httpRequest : collector)
        storageService.updateOrCreate(httpRequest.getUrl(), httpRequest.getTitle(), httpRequest.getClearText());
    }

    return getView(InitializeUrl.INDEX_VIEW);
  }
}
