package com.svisoft.simplegoogle.core.storage.itest;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class StorageService_getAllTest
    extends StorageServiceTest
{
  @Test
  public void should_return_empty_list_if_there_is_no_documents()
      throws
      Exception
  {
    assertEquals(searchService.getAll().size(), 0);
  }

  @Test
  public void should_return_all_added_documents()
      throws
      Exception
  {
    indexService.index("http://url.com", "title1",  "1");
    indexService.index("http://url.com/index", "title2",  "2");
    indexService.index("http://url.com/index/index", "title3",  "3");
    indexService.index("http://url.com/index/index/index", "title4",  "4");
    assertEquals(searchService.getAll().size(), 4);
  }
}
