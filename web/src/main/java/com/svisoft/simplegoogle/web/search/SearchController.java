package com.svisoft.simplegoogle.web.search;

import com.svisoft.common.web.AbstractController;
import com.svisoft.simplegoogle.core.storage.StorageService;
import com.svisoft.simplegoogle.web.storage.SimplegoogleDocumentWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(SearchUrl.PREFIX)
public class SearchController
    extends AbstractController
{
  private StorageService storageService;

  public void setStorageService(StorageService storageService)
  {
    this.storageService = storageService;
  }

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

  @RequestMapping(SearchUrl.SEARCH)
	public String search(
      @RequestParam(defaultValue = " ") String query,
      Model model,
      WebRequest request,
      HttpSession session
  )
      throws
      Exception
  {
    model.addAttribute("documents", SimplegoogleDocumentWrapper.wrapDocuments(storageService.search(query, 10)));

    return getView(SearchUrl.SEARCH_VIEW);
	}
}
