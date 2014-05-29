package com.svisoft.simplegoogle.web.indexing;

import com.svisoft.common.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(IndexingUrl.PREFIX)
public class IndexingController
    extends AbstractController
{
	@RequestMapping(IndexingUrl.INDEX)
	public String printWelcome(
      Model model,
      WebRequest request,
      HttpSession session
  )
  {
		model.addAttribute("message", "Indexing page");

    return getView(IndexingUrl.INDEX_VIEW);
	}
}