package com.svisoft.simplegoogle.core.storage;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class StorageConfigFactory
{
  public static final Version SIMPLEGOOGLE_LUCENE_VERSION = Version.LUCENE_46;
  public static final String ID_FIELD_NAME = "id";
  public static final String VALUE_FIELD_NAME = "value";

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
          //TODO: introduce own analyzer. Something like WhiteSpace+Simple analyzer :)
//          analyzer = new StandardAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION);
          analyzer = new WhitespaceAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION);
//          analyzer = new SimpleAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION);
      }

    return analyzer;
  }

  public static IndexWriterConfig getIndexWriterConfig()
  {
    IndexWriterConfig config = new IndexWriterConfig(SIMPLEGOOGLE_LUCENE_VERSION, getAnalyzer());
//    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

    return config;
  }
}
