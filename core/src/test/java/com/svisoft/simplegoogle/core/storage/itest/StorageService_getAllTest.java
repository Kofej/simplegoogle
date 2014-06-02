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
    storageService.updateOrCreate("1", "1");
    storageService.updateOrCreate("2", "2");
    storageService.updateOrCreate("3", "3");
    storageService.updateOrCreate("4", "4");
    assertEquals(storageService.getAll().size(), 4);
  }
}
