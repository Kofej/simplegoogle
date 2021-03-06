package com.svisoft.simplegoogle.web.search;

import com.svisoft.common.web.AbstractController;
import com.svisoft.simplegoogle.core.storage.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(SearchUrl.PREFIX)
public class SearchController
    extends AbstractController
{
  private SearchService searchService;

  public void setSearchService(SearchService searchService)
  {
    this.searchService = searchService;
  }

  @RequestMapping(SearchUrl.INDEX)
	public String index()
  {
    return getView(SearchUrl.INDEX_VIEW);
	}

  @RequestMapping(SearchUrl.SEARCH)
	public String search(
      @RequestParam(defaultValue = "") String query,
      Model model
  )
      throws
      Exception
  {
    if (!query.equals(""))
      model.addAttribute("documents", searchService.search(query));
    model.addAttribute("query", query);

    return getView(SearchUrl.SEARCH_VIEW);
	}
}
