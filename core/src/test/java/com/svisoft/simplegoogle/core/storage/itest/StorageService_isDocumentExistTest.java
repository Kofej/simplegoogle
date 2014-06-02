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
    storageService.updateOrCreate("1", "1");
    assertTrue(storageService.isDocumentExist("1"));
  }
}