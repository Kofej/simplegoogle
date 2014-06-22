package com.svisoft.simplegoogle.core.storage;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;

public class DocumentBuilder
{
  private Document document;

  public DocumentBuilder()
  {
    this.document = new Document();
  }

  public DocumentBuilder add(
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
