package com.svisoft.simplegoogle.core.impl.storage;

import com.svisoft.simplegoogle.core.storage.DocumentBuilder;
import com.svisoft.simplegoogle.core.storage.SimplegoogleDocument;
import com.svisoft.simplegoogle.core.storage.StorageDao;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.util.ArrayList;
import java.util.List;

public class StorageDaoImpl
    implements StorageDao
{
  public static final Version SIMPLEGOOGLE_LUCENE_VERSION = Version.LUCENE_46;
  private String idFieldName;
  private String titleFieldName;
  private String contextFieldName;
  private Directory directory;
  private Analyzer analyzer;

  public void setIdFieldName(String idFieldName)
  {
    this.idFieldName = idFieldName;
  }

  public void setTitleFieldName(String titleFieldName)
  {
    this.titleFieldName = titleFieldName;
  }

  public void setContextFieldName(String contextFieldName)
  {
    this.contextFieldName = contextFieldName;
  }

  @Override
  public List<Document> getAll()
      throws
      Exception
  {
    if (getDirectory().listAll().length == 0)
      return new ArrayList<Document>();
    IndexReader reader = DirectoryReader.open(getDirectory());
    List<Document> result = new ArrayList<Document>(reader.maxDoc());
    for (int i = 0; i < reader.maxDoc(); i++)
    {
      result.add(reader.document(i));
    }
    return result;
  }

  @Override
  public Boolean isDocumentExist(String id)
      throws
      Exception
  {
    PhraseQuery q = new PhraseQuery();
    q.add(new Term(idFieldName, id));
    if (getDirectory().listAll().length == 0)
      return false;
    IndexReader reader = DirectoryReader.open(getDirectory());
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(1, true);
    searcher.search(q, collector);
    reader.close();

    return collector.topDocs().scoreDocs.length != 0;
  }

  @Override
  public List<SimplegoogleDocument> search(String query, int count)
      throws
      Exception
  {
    List<SimplegoogleDocument> result = new ArrayList<SimplegoogleDocument>();
    if (getDirectory().listAll().length == 0)
      return result;
    Query q = new QueryParser(SIMPLEGOOGLE_LUCENE_VERSION, contextFieldName, getAnalyzer())
        .parse(QueryParser.escape(query));
    IndexReader reader = DirectoryReader.open(getDirectory());
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(count, true);
    searcher.search(q, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    SimplegoogleDocument sDocument;
    for (ScoreDoc scoreDoc : hits)
    {
      Document doc = searcher.doc(scoreDoc.doc);
      sDocument = new SimplegoogleDocument();
      sDocument.setId(doc.get(idFieldName));
      sDocument.setTitle(doc.get(titleFieldName));
      sDocument.setContext(doc.get(contextFieldName));
      sDocument.setText("text");
      result.add(sDocument);
    }
    reader.close();

    return result;
  }

  @Override
  public void create(String id, String title, String context)
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(getDirectory(), new IndexWriterConfig(SIMPLEGOOGLE_LUCENE_VERSION, getAnalyzer()));
    Document doc = new DocumentBuilder()
        .add(idFieldName, id, StringField.TYPE_STORED)
        .add(titleFieldName, title, TextField.TYPE_STORED)
        .add(contextFieldName, context, TextField.TYPE_NOT_STORED)
        .build();
    writer.addDocument(doc);
    writer.commit();
    writer.close();
  }

  @Override
  public void update(String id, String title, String context)
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(getDirectory(), new IndexWriterConfig(SIMPLEGOOGLE_LUCENE_VERSION, getAnalyzer()));
    Document doc = new DocumentBuilder()
        .add(idFieldName, id, StringField.TYPE_STORED)
        .add(titleFieldName, title, TextField.TYPE_STORED)
        .add(contextFieldName, context, TextField.TYPE_NOT_STORED)
        .build();
    writer.updateDocument(new Term(idFieldName, id), doc);
    writer.close();
  }

  @Override
  public void deleteById(String id)
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(getDirectory(), new IndexWriterConfig(SIMPLEGOOGLE_LUCENE_VERSION, getAnalyzer()));
    writer.deleteDocuments(new Term(idFieldName, id));
    writer.close();
  }

  @Override
  public void deleteAll()
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(getDirectory(), new IndexWriterConfig(SIMPLEGOOGLE_LUCENE_VERSION, getAnalyzer()));
    writer.deleteAll();
    writer.close();
  }

  private Directory getDirectory()
  {
    if (directory == null)
      synchronized (this)
      {
        if (directory == null)
          directory = new RAMDirectory();
      }

    return directory;
  }

  private Analyzer getAnalyzer()
  {
    if (analyzer == null)
      synchronized(this)
      {
        if (analyzer == null)
          //TODO:Analyzer: introduce own analyzer. Something like WhiteSpace+Simple analyzer
          //analyzer = new StandardAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION); // doesn't tokenize 'stop words'
          analyzer = new WhitespaceAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION);
          //analyzer = new SimpleAnalyzer(SIMPLEGOOGLE_LUCENE_VERSION);   // doesn't tokenize numbers
      }

    return analyzer;
  }
}
