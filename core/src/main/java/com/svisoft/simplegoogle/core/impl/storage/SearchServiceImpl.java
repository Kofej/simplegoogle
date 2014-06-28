package com.svisoft.simplegoogle.core.impl.storage;

import com.svisoft.simplegoogle.core.storage.SearchService;
import com.svisoft.simplegoogle.core.storage.SimplegoogleDocument;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl
    implements SearchService
{
  private Directory directory;
  private Analyzer analyzer;
  private Version luceneVersion;
  private String idFieldName;
  private String titleFieldName;
  private String contextFieldName;

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

  public void setContextFieldName(String contextFieldName)
  {
    this.contextFieldName = contextFieldName;
  }

  @Override
  public List<Document> getAll() throws Exception
  {
    if (directory.listAll().length == 0)
      return new ArrayList<Document>();
    IndexReader reader = DirectoryReader.open(directory);
    List<Document> result = new ArrayList<Document>(reader.maxDoc());
    for (int i = 0; i < reader.maxDoc(); i++)
    {
      result.add(reader.document(i));
    }

    return result;
  }

  @Override
  public Boolean isDocumentExist(String id) throws Exception
  {
    PhraseQuery q = new PhraseQuery();
    q.add(new Term(idFieldName, id));
    if (directory.listAll().length == 0)
      return false;
    IndexReader reader = DirectoryReader.open(directory);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(1, true);
    searcher.search(q, collector);
    reader.close();

    return collector.topDocs().scoreDocs.length != 0;
  }

  @Override
  public List<SimplegoogleDocument> search(String query, int count) throws Exception
  {
    List<SimplegoogleDocument> result = new ArrayList<SimplegoogleDocument>();
    if (directory.listAll().length == 0)
      return result;
    Query q = new QueryParser(luceneVersion, contextFieldName, analyzer)
        .parse(QueryParser.escape(query));
    IndexReader reader = DirectoryReader.open(directory);
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
      result.add(sDocument);
    }
    reader.close();

    return result;
  }
}
