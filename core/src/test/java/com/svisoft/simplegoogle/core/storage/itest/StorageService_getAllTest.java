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
    assertEquals(storageService.getAll().size(), 0);
  }

  @Test
  public void should_return_all_added_documents()
      throws
      Exception
  {
    storageService.updateOrCreate("http://url.com", "title1",  "1");
    storageService.updateOrCreate("http://url.com/index", "title2",  "2");
    storageService.updateOrCreate("http://url.com/index/index", "title3",  "3");
    storageService.updateOrCreate("http://url.com/index/index/index", "title4",  "4");
    assertEquals(storageService.getAll().size(), 4);
  }
}
