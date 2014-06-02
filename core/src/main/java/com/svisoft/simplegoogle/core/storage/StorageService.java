package com.svisoft.simplegoogle.core.storage;

import org.apache.lucene.document.Document;

import java.util.List;

public interface StorageService
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

  void updateOrCreate(String id, String value)
      throws
      Exception;

  //----------------------------------------------------------------------
  // Utilz
  //----------------------------------------------------------------------
}
