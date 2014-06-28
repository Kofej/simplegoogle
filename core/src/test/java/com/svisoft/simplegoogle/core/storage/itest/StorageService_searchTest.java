package com.svisoft.simplegoogle.core.storage.itest;

import com.google.common.collect.Lists;
import org.apache.lucene.document.Document;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class StorageService_searchTest
    extends StorageServiceTest
{
  @Test
  public void should_return_documents_that_match_to_search_query()
      throws
      Exception
  {
    indexService.index("http://url.com", "title", "some string for search.Some stop words: do in for");
    assertEquals(searchService.search("for", 10).size(), 1);
    assertEquals(searchService.search("some", 10).size(), 1);
    assertEquals(searchService.search("some string", 10).size(), 1);
    assertEquals(searchService.search("string some while", 10).size(), 1);
    assertEquals(searchService.search("string do in", 10).size(), 1);
//    assertEquals(searchService.search("title", 10).size(), 1);
//    assertEquals(searchService.search("titel some", 10).size(), 1);
//    assertEquals(searchService.search("search", 10).size(), 1);  :-(
  }

  @DataProvider
  Object[][] valuesToStore()
  {
    return new Object[][]
        {
            {Lists.newArrayList("1", "1 2", "2 1 3", "2 3 4 1") , 1, "1"},
            {Lists.newArrayList("1", "1 2", "2 1 3", "2 3 4 1") , 2, "1"},
            {Lists.newArrayList("1", "1 2", "2 1 3", "2 3 4 1") , 3, "1"},
            {Lists.newArrayList("1", "1 2", "2 1 3", "2 3 4 1") , 4, "1"},
        };
  }

  @Test(dataProvider = "valuesToStore")
  public void should_return_specified_number_of_documents(List<String> valuesToStore, int count, String query)
      throws
      Exception
  {
    int i = 0;
    for (String value : valuesToStore)
      indexService.index(Integer.toString(i++), "title", value);
    assertEquals(searchService.search(query, count).size(), count);
  }
}
