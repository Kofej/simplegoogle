package com.svisoft.simplegoogle.core.impl.storage;

import com.svisoft.simplegoogle.core.storage.SimplegoogleDocumentBuilder;
import com.svisoft.simplegoogle.core.storage.StorageConfigFactory;
import com.svisoft.simplegoogle.core.storage.StorageDao;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
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
    if (StorageConfigFactory.getDirectory().listAll().length == 0)
      return result;
    MultiFieldQueryParser q = new MultiFieldQueryParser(
        StorageConfigFactory.SIMPLEGOOGLE_LUCENE_VERSION,
        new String[] { StorageConfigFactory.TITLE_FIELD_NAME , StorageConfigFactory.CONTEXT_FIELD_NAME },
        StorageConfigFactory.getAnalyzer()
    );
    IndexReader reader = DirectoryReader.open(StorageConfigFactory.getDirectory());
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(count, true);
    searcher.search(q.parse(MultiFieldQueryParser.escape(query)), collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    for (ScoreDoc scoreDoc : hits)
      result.add(searcher.doc(scoreDoc.doc));
    reader.close();

    return result;
  }

  @Override
  public void create(String id, String title, String context)
      throws
      Exception
  {
    IndexWriter writer = new IndexWriter(StorageConfigFactory.getDirectory(), StorageConfigFactory.getIndexWriterConfig());
    Document doc = new SimplegoogleDocumentBuilder()
        .add(StorageConfigFactory.ID_FIELD_NAME, id, StorageConfigFactory.ID_FIELD_TYPE)
        .add(StorageConfigFactory.TITLE_FIELD_NAME, title, StorageConfigFactory.TITLE_FIELD_TYPE)
        .add(StorageConfigFactory.CONTEXT_FIELD_NAME, context, StorageConfigFactory.CONTEXT_FIELD_TYPE)
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
    IndexWriter writer = new IndexWriter(StorageConfigFactory.getDirectory(), StorageConfigFactory.getIndexWriterConfig());
    Document doc = new SimplegoogleDocumentBuilder()
        .add(StorageConfigFactory.ID_FIELD_NAME, id, StorageConfigFactory.ID_FIELD_TYPE)
        .add(StorageConfigFactory.TITLE_FIELD_NAME, title, StorageConfigFactory.TITLE_FIELD_TYPE)
        .add(StorageConfigFactory.CONTEXT_FIELD_NAME, context, StorageConfigFactory.CONTEXT_FIELD_TYPE)
        .build();
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
