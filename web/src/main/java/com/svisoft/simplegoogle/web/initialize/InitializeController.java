package com.svisoft.simplegoogle.web.initialize;

import com.svisoft.common.web.AbstractController;
import com.svisoft.simplegoogle.core.initialize.InitializeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

  @RequestMapping(value = InitializeUrl.INDEX)
  public String initializeByUrl(
      Model model,
      @RequestParam(required = false) String url,
      @RequestParam(required = false) Integer depth
  )
  {
    if (url != null && depth != null)
      initializeService.initByUrl(url, depth);
    model.addAttribute("url", url);

    return getView(InitializeUrl.INDEX_VIEW);
  }
}
