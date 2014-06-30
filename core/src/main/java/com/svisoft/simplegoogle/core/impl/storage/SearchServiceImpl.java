package com.svisoft.simplegoogle.core.impl.storage;

import com.svisoft.simplegoogle.core.storage.SearchService;
import com.svisoft.simplegoogle.core.storage.SimplegoogleDocument;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.search.spans.Spans;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

import java.util.*;

public class SearchServiceImpl
    implements SearchService
{
  private Directory directory;
  private String idFieldName;
  private String titleFieldName;
  private String contextFieldName;

  public void setDirectory(Directory directory)
  {
    this.directory = directory;
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
  public List<SimplegoogleDocument> search(String query) throws Exception
  {
    List<SimplegoogleDocument> result = new ArrayList<SimplegoogleDocument>();
    if (directory.listAll().length == 0)
      return result;
    Set<Integer> c = new HashSet<Integer>();
    for (String queryPart : query.split(" "))
    {
      SpanTermQuery q = new SpanTermQuery(new Term(contextFieldName, queryPart));
      IndexReader reader = DirectoryReader.open(directory);
      IndexSearcher searcher = new IndexSearcher(reader);
      AtomicReader wrapper = SlowCompositeReaderWrapper.wrap(reader);
      Map<Term, TermContext> termContexts = new HashMap<Term, TermContext>();
      Spans spans = q.getSpans(wrapper.getContext(), new Bits.MatchAllBits(reader.numDocs()), termContexts);
      SimplegoogleDocument sDocument;
      while (spans.next())
      {
        if (c.contains(spans.doc())) continue;
        c.add(spans.doc());
        Document doc = searcher.doc(spans.doc());
        int start = spans.start() - 5;
        int end = spans.end() + 5;
        Terms content = reader.getTermVector(spans.doc(), contextFieldName);
        TermsEnum termsEnum = content.iterator(null);
        BytesRef term;
        Map<Integer, String> entries = new TreeMap<Integer, String>();
        while ((term = termsEnum.next()) != null)
        {
          String s = new String(term.bytes, term.offset, term.length);
          DocsAndPositionsEnum positionsEnum = termsEnum.docsAndPositions(null, null);
          if (positionsEnum.nextDoc() != DocIdSetIterator.NO_MORE_DOCS)
          {
            int i = 0;
            int position;
            while (i++ < positionsEnum.freq() && (position = positionsEnum.nextPosition()) != -1)
            {
              if (position >= start && position <= end)
                entries.put(position, s);
            }
          }
        }
        StringBuilder sb = new StringBuilder();
        for (Integer key : entries.keySet())
          sb.append(entries.get(key)).append(" ");

        sDocument = new SimplegoogleDocument();
        sDocument.setId(doc.get(idFieldName));
        sDocument.setTitle(doc.get(titleFieldName));
        sDocument.setPreviewText(sb.toString());
        result.add(sDocument);
      }
      reader.close();
    }

    return result;
  }
}
