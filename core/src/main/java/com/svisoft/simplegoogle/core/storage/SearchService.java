package com.svisoft.simplegoogle.core.storage;

import org.apache.lucene.document.Document;

import java.util.List;

public interface SearchService
{
  List<Document> getAll()
      throws
      Exception;

  Boolean isDocumentExist(String id)
      throws
      Exception;

  List<SimplegoogleDocument> search(String query, int count)
      throws
      Exception;
}
