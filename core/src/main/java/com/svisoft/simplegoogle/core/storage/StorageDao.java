package com.svisoft.simplegoogle.core.storage;

import org.apache.lucene.document.Document;

import java.util.List;

public interface StorageDao
{
  //----------------------------------------------------------------------
  // Reading
  //----------------------------------------------------------------------

  List<Document> getAll()
      throws
      Exception;

  Boolean isDocumentExist(String id)
      throws
      Exception;

  List<SimplegoogleDocument> search(String query, int count)
      throws
      Exception;

  //----------------------------------------------------------------------
  // Modification
  //----------------------------------------------------------------------

  void create(String id, String title, String context)
      throws
      Exception;

  void update(String id, String title, String context)
      throws
      Exception;

  void deleteById(String id)
      throws
      Exception;

  void deleteAll()
      throws
      Exception;

  //----------------------------------------------------------------------
  // Utilz
  //----------------------------------------------------------------------
}
