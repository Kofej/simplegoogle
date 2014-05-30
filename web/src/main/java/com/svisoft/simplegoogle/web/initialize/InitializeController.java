package com.svisoft.simplegoogle.web.initialize;

import com.svisoft.common.web.AbstractController;
import com.svisoft.simplegoogle.core.initialize.InitializeService;
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
  private InitializeService initializeService;

  public void setInitializeService(InitializeService initializeService)
  {
    this.initializeService = initializeService;
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
  public String initialize(
      Model model,
      WebRequest request,
      HttpSession session
  )
      throws Exception
  {
    initializeService.initByUrl(request.getParameter("uri"));

    return getView(InitializeUrl.INDEX_VIEW);
  }
}
