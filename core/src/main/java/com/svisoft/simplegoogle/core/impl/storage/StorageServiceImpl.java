package com.svisoft.simplegoogle.core.impl.storage;

import com.svisoft.simplegoogle.core.storage.SimplegoogleDocument;
import com.svisoft.simplegoogle.core.storage.StorageService;
import com.svisoft.simplegoogle.core.storage.StorageDao;
import org.apache.lucene.document.Document;

import java.util.List;

public class StorageServiceImpl
    implements StorageService
{
  private StorageDao storageDao;

  public void setStorageDao(StorageDao storageDao)
  {
    this.storageDao = storageDao;
  }

  @Override
  public List<Document> getAll()
      throws
      Exception
  {
    return storageDao.getAll();
  }

  @Override
  public Boolean isDocumentExist(String id)
      throws
      Exception
  {
    return storageDao.isDocumentExist(id);
  }

  @Override
  public List<SimplegoogleDocument> search(String query, int count)
      throws
      Exception
  {
    return storageDao.search(query, count);
  }

  @Override
  public void updateOrCreate(String id, String title, String context)
      throws
      Exception
  {
    if (isDocumentExist(id))
      storageDao.update(id, title, context);
    else
      storageDao.create(id, title, context);
  }
}
