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

  List<Document> search(String query, int count)
      throws
      Exception;

  //----------------------------------------------------------------------
  // Modification
  //----------------------------------------------------------------------

  void create(String id, String value)
      throws
      Exception;

  void update(String id, String value)
      throws
      Exception;

  void delete(String id)
      throws
      Exception;

  void deleteAll()
      throws
      Exception;

  //----------------------------------------------------------------------
  // Utilz
  //----------------------------------------------------------------------
}