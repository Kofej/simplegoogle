package com.svisoft.simplegoogle.core.storage.itest;

import com.svisoft.simplegoogle.core.storage.SimplegoogleDocument;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class StorageService_updateOrCreateTest
    extends StorageServiceTest
{
  @Test
  public void should_create_existing_document_if_it_does_not_exist()
      throws
      Exception
  {
    storageService.updateOrCreate("http://url.com", "title", "context");
    List<SimplegoogleDocument> result = storageService.search("context", 2);
    assertEquals(result.size(), 1);
  }

  @Test
  public void should_update_existing_document_if_it_is_exist()
      throws
      Exception
  {
    storageService.updateOrCreate("http://url.com", "title",  "old");
    storageService.updateOrCreate("http://url.com", "title",  "new value");
    List<SimplegoogleDocument> result = storageService.search("old", 2);
    assertEquals(result.size(), 0);
    result = storageService.search("new value", 2);
    assertEquals(result.size(), 1);
  }
}
