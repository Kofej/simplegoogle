package com.svisoft.simplegoogle.core.storage;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;

public class SimplegoogleDocumentBuilder
{
  private Document document;

  public SimplegoogleDocumentBuilder()
  {
    this.document = new Document();
  }

  public SimplegoogleDocumentBuilder add(
      String name,
      String value,
      FieldType fieldType
  )
  {
    document.add(new Field(name, value, fieldType));
    return this;
  }

  public Document build()
  {
    return this.document;
  }
}
