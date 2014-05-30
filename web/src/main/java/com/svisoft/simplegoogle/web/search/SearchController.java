package com.svisoft.simplegoogle.web.search;

import com.svisoft.common.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(SearchUrl.PREFIX)
public class SearchController
    extends AbstractController
{
	@RequestMapping(SearchUrl.INDEX)
	public String index(
      Model model,
      WebRequest request,
      HttpSession session
  )
  {
		model.addAttribute("message", "Search page");

    return getView(SearchUrl.INDEX_VIEW);
	}
}
