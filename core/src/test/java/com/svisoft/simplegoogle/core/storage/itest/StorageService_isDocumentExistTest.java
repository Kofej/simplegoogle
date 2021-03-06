package com.svisoft.simplegoogle.core.storage.itest;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class StorageService_isDocumentExistTest
    extends StorageServiceTest
{
  @Test
  public void should_return_true_if_documents_exist()
      throws
      Exception
  {
    indexService.index("http://url.com", "title", "1");
    assertTrue(searchService.isDocumentExist("http://url.com"));
  }
}
