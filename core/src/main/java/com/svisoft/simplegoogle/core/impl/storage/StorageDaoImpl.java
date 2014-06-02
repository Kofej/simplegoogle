package com.svisoft.simplegoogle.core.impl.storage;

import com.svisoft.simplegoogle.core.storage.StorageConfigFactory;
import com.svisoft.simplegoogle.core.storage.StorageDao;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import java.util.ArrayList;
import java.util.List;

public class StorageDaoImpl
    implements StorageDao
{
  @Override
  public List<Document> getAll()
      throws
      Exception
  {
    if (StorageConfigFactory.getDirectory().listAll().length == 0)
      return new ArrayList<Document>();
    IndexReader reader = DirectoryReader.open(StorageConfigFactory.getDirectory());
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
//    Query q = new QueryParser(StorageConfigFactory.getLuceneVersion(), StorageConfigFactory.getIdFieldName(), StorageConfigFactory.getAnalyzer())
//        .parse(QueryParser.escape(id));
//    TermQuery q = new TermQuery(new Term(StorageConfigFactory.getIdFieldName(), id));
    PhraseQuery q = new PhraseQuery();
    q.add(new Term(StorageConfigFactory.ID_FIELD_NAME, id));
    if (StorageConfigFactory.getDirectory().listAll().length == 0)
      return false;
    IndexReader reader = DirectoryReader.open(StorageConfigFactory.getDirectory());
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(1, true);
    searcher.search(q, collector);
    reader.close();

    return collector.topDocs().scoreDocs.length != 0;
  }

  @Override
  public List<Document> search(String query, int count)
      throws
      Exception
  {
    List<Document> result = new ArrayList<Document>();
    Query q = new QueryParser(StorageConfigFactory.SIMPLEGOOGLE_LUCENE_VERSION, StorageConfigFactory.VALUE_FIELD_NAME, StorageConfigFactory.getAnalyzer())
        .parse(QueryParser.escape(query));
//    WildcardQuery q = new WildcardQuery(new Term(StorageConfigFactory.getValueFieldName(), query.toLowerCase()+"*"));
//    TermQuery q = new TermQuery(new Term(StorageConfigFactory.getValueFieldName(), query));
    if (StorageConfigFactory.getDirectory().listAll().length == 0)
      return result;
    IndexReader reader = DirectoryReader.open(StorageConfigFactory.getDirectory());
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(count, true);
    searcher.search(q, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    for (ScoreDoc scoreDoc : hits)
      result.add(searcher.doc(scoreDoc.doc));
    reader.close();

    return result;
  }

  @Override
  public void create(String id, String value)
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(StorageConfigFactory.getDirectory(), StorageConfigFactory.getIndexWriterConfig());
    Document doc = new Document();
    doc.add(new Field(StorageConfigFactory.ID_FIELD_NAME, id, StringField.TYPE_STORED));
    doc.add(new Field(StorageConfigFactory.VALUE_FIELD_NAME, value, TextField.TYPE_STORED));
    writer.addDocument(doc);
    writer.commit();
    writer.close();
  }

  @Override
  public void update(String id, String value)
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(StorageConfigFactory.getDirectory(), StorageConfigFactory.getIndexWriterConfig());
    Document doc = new Document();
    doc.add(new Field(StorageConfigFactory.ID_FIELD_NAME, id, StringField.TYPE_STORED));
    doc.add(new Field(StorageConfigFactory.VALUE_FIELD_NAME, value, TextField.TYPE_STORED));
    writer.updateDocument(new Term(StorageConfigFactory.ID_FIELD_NAME, id), doc);
    writer.close();
  }

  @Override
  public void delete(String id)
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(StorageConfigFactory.getDirectory(), StorageConfigFactory.getIndexWriterConfig());
    writer.deleteDocuments(new Term(StorageConfigFactory.ID_FIELD_NAME, id));
    writer.close();
  }

  @Override
  public void deleteAll()
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(StorageConfigFactory.getDirectory(), StorageConfigFactory.getIndexWriterConfig());
    writer.deleteAll();
    writer.close();
  }
}
