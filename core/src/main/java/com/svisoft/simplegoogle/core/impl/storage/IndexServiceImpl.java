package com.svisoft.simplegoogle.core.impl.storage;

import com.svisoft.simplegoogle.core.storage.DocumentBuilder;
import com.svisoft.simplegoogle.core.storage.IndexService;
import com.svisoft.simplegoogle.core.storage.SearchService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

public class IndexServiceImpl
    implements IndexService
{
  private Directory directory;
  private Analyzer analyzer;
  private Version luceneVersion;
  private String idFieldName;
  private String titleFieldName;
  private String contentFieldName;
  private SearchService searchService;

  public void setDirectory(Directory directory)
  {
    this.directory = directory;
  }

  public void setAnalyzer(Analyzer analyzer)
  {
    this.analyzer = analyzer;
  }

  public void setLuceneVersion(Version luceneVersion)
  {
    this.luceneVersion = luceneVersion;
  }

  public void setIdFieldName(String idFieldName)
  {
    this.idFieldName = idFieldName;
  }

  public void setTitleFieldName(String titleFieldName)
  {
    this.titleFieldName = titleFieldName;
  }

  public void setContentFieldName(String contentFieldName)
  {
    this.contentFieldName = contentFieldName;
  }

  public void setSearchService(SearchService searchService)
  {
    this.searchService = searchService;
  }

  @Override
  public void index(String id, String title, String content) throws Exception
  {
    IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(luceneVersion, analyzer));
    FieldType sFieldType = new FieldType();
    sFieldType.setStoreTermVectors(true);
    sFieldType.setStoreTermVectorOffsets(true);
    sFieldType.setStoreTermVectorPositions(true);
    sFieldType.setIndexed(true);
    sFieldType.setTokenized(true);
    Document doc = new DocumentBuilder()
        .add(idFieldName, id, StringField.TYPE_STORED)
        .add(titleFieldName, title, TextField.TYPE_STORED)
        .add(contentFieldName, content, sFieldType)
        .build();
    if (searchService.isDocumentExist(id))
      writer.updateDocument(new Term(idFieldName, id), doc);
    else
      writer.addDocument(doc);

    writer.commit();
    writer.close();
  }

  @Override
  public void deleteById(String id)
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(luceneVersion, analyzer));
    writer.deleteDocuments(new Term(idFieldName, id));
    writer.close();
  }

  @Override
  public void deleteAll()
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(luceneVersion, analyzer));
    writer.deleteAll();
    writer.close();
  }
}
