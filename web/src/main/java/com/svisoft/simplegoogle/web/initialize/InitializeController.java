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
      Model model,
      @RequestParam(required = false) String url,
      @RequestParam(required = false) Integer depth
  )
      throws
      Exception
  {
    if (url != null)
    {
      Set<SimplegoogleHttpRequest> collector = new HashSet<SimplegoogleHttpRequest>();
      SimplegoogleHttpRequest.deepScan(Sets.newHashSet(new SimplegoogleHttpRequest(url)), depth, collector);
      for (SimplegoogleHttpRequest httpRequest : collector)
        storageService.updateOrCreate(httpRequest.getUrl(), httpRequest.getTitle(), httpRequest.getClearText());
    }
    model.addAttribute("url", url);

    return getView(InitializeUrl.INDEX_VIEW);
  }
}
