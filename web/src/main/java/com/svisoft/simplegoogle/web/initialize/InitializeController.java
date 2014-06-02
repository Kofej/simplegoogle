package com.svisoft.simplegoogle.web.initialize;

import com.svisoft.common.web.AbstractController;
import com.svisoft.simplegoogle.core.request.SimplegoogleHttpRequest;
import com.svisoft.simplegoogle.core.storage.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

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

  @RequestMapping(InitializeUrl.INDEX)
  public String index(
      Model model,
      WebRequest request,
      HttpSession session
  )
  {
    return getView(InitializeUrl.INDEX_VIEW);
  }

  @RequestMapping(value = InitializeUrl.INDEX, method = RequestMethod.POST)
  public String initializeByUrl(
      Model model,
      WebRequest request,
      HttpSession session
  )
      throws Exception
  {
    // TODO:Index: deep indexing currently not supported
    String uri = request.getParameter("uri");
    SimplegoogleHttpRequest httpRequest = new SimplegoogleHttpRequest(uri).sendRequest();
    storageService.updateOrCreate(httpRequest.getUrl(), httpRequest.getTitle(), httpRequest.getClearText());

    return getView(InitializeUrl.INDEX_VIEW);
  }
}
