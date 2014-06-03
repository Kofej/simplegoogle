package com.svisoft.simplegoogle.core.storage;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class StorageConfigFactory
{
  public static final Version SIMPLEGOOGLE_LUCENE_VERSION = Version.LUCENE_46;
  public static final String ID_FIELD_NAME = "id";
  public static final FieldType ID_FIELD_TYPE = StringField.TYPE_STORED;
  public static final String TITLE_FIELD_NAME = "title";
  public static final FieldType TITLE_FIELD_TYPE = TextField.TYPE_STORED;
  public static final String CONTEXT_FIELD_NAME = "context";
  public static final FieldType CONTEXT_FIELD_TYPE = TextField.TYPE_NOT_STORED;

  private static Directory directory;
  private static Analyzer analyzer;

  public static Directory getDirectory()
  {
    if (directory == null)
      synchronized (StorageConfigFactory.class)
      {
        if (directory == null)
          directory = new RAMDirectory();
      }

    return directory;
  }

  public static Analyzer getAnalyzer()
  {
    if (analyzer == null)
      synchronized (StorageConfigFactory.class)
      {
        if (analyzer == null)
          //TODO:Analyzer: introduce own analyzer. Something like WhiteSpace+Simple analyzer
//          analyzer = new StandardAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION); // doesn't tokenize 'stop words'
          analyzer = new WhitespaceAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION);
//          analyzer = new SimpleAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION);   // doesn't tokenize numbers
      }

    return analyzer;
  }

  public static IndexWriterConfig getIndexWriterConfig()
  {
    IndexWriterConfig config = new IndexWriterConfig(SIMPLEGOOGLE_LUCENE_VERSION, getAnalyzer());
//    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

    return config;
  }
}
