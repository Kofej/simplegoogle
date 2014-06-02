package com.svisoft.simplegoogle.web.storage;

import com.svisoft.simplegoogle.core.storage.StorageConfigFactory;
import org.apache.lucene.document.Document;

import java.util.ArrayList;
import java.util.List;

public class SimplegoogleDocumentWrapper
{
  private String id;
  private String title;
  private Document document;

  public SimplegoogleDocumentWrapper(Document document)
  {
    this.document = document;
    this.id = document.getField(StorageConfigFactory.ID_FIELD_NAME).stringValue();
    this.title = document.getField(StorageConfigFactory.TITLE_FIELD_NAME).stringValue();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public Document getDocument()
  {
    return document;
  }

  public void setDocument(Document document)
  {
    this.document = document;
  }

  public static List<SimplegoogleDocumentWrapper> wrapDocuments(List<Document> documents)
  {
    List<SimplegoogleDocumentWrapper> result = new ArrayList<SimplegoogleDocumentWrapper>(documents.size());
    for (Document document : documents)
    {
      result.add(new SimplegoogleDocumentWrapper(document));
    }

    return result;
  }
}
