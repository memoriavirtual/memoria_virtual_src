package org.mvirtual.persistence.hibernate.search;

import java.util.*;

import org.mvirtual.persistence.PersistenceStatics;
import org.mvirtual.persistence.entity.Entity;
import org.mvirtual.persistence.search.AbstractPagedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.Search;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.FullTextQuery;

import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.BooleanClause;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HibernateQuery<T extends Entity>
	extends AbstractPagedQuery<T>
	implements PersistenceStatics
{
	private final Logger log = LoggerFactory.getLogger(HibernateQuery.class);

	// internal
	private List<T> result;
	private Class<T> modelClazz;
	// TODO: In a static{} context, introspect models annotations to findout
	// fields available for sorting [define a convention: those ended with "_forSort"]
	private static final Map<String, String> sortTable =
			new HashMap<String, String>(){{
				put("author", "authorityHeritages.authority.authorityName_forSort");
				put("title", "heritageTitle_forSort");
			}};
	// hibernate
	private Session session;
	private Transaction tx;
	private FullTextSession fullTextSession;
	// options
	private String sort = LUCENE_DEFAULT_SORTFIELD;
	private String[] fields = LUCENE_DEFAULT_FIELDS;
	private String[] queries = {};
	// lucene
	private BooleanClause.Occur[] flags;
	private FullTextQuery fullTextQuery;

	// utilitary methods

	protected Sort getLuceneSort(String s)
	{
		s = s.toLowerCase();
		return (sortTable.containsKey(s) ? new Sort(new SortField(sortTable.get(s))) : 
			Sort.RELEVANCE);
	}

	protected FullTextSession getFullTextSession()
	{
		if ((fullTextSession == null) || (!fullTextSession.isOpen())) {
		
			if (session == null)
				throw new RuntimeException("Cannot get FullTextSession: Session is null");

			if (!session.isOpen())
				throw new RuntimeException("Cannot get FullTextSession: Session is closed");

			fullTextSession = Search.getFullTextSession(session);
		}

		return fullTextSession;
	}
	
	protected String filterQuery(String q) {
		return (q != null ? q.replaceAll(LUCENE_STRIP_FROM_QUERY_REGEX, "") : q);
	}

	protected String[] filterQueries(String[] queries)
	{
		String[] newQueries = new String[queries.length];
		for (int i = 0; i < queries.length; i++)
			newQueries[i] = filterQuery(queries[i]);
		return newQueries;
	}

	// public fields and methods
	
	public static Set<String> SORTABLE_FIELDS = sortTable.keySet();
	
	public HibernateQuery() {
		modelClazz = getModelClass();
	}

	public Session getSession() { return session; }
	public void setSession(Session s) { session = s; }
	
	public void setFields(String[] fs) { fields = fs; }
	public String[] getFields() { return fields; }
	
	public void setFlags(BooleanClause.Occur[] fs) { flags = fs; }
	public BooleanClause.Occur[] getFlags() { return flags; }

	public String getSort() { return sort; }
	public void setSort(String s) {
		s = s.toLowerCase();
		sort = (sortTable.containsKey(s) ? s : "relevance");
	}
	
	public String getQuery() { return (queries.length > 0 ? queries[0] : null); }
	public void setQuery(String query) { setMultiQuery(new String[]{query}); }

	public String[] getMultiQuery() { return queries; }
	public void setMultiQuery(String[] query) { queries = query; }

	public List<T> getResult() { return result; }
	
	public void execute() {
		if ((queries == null) || (queries.length == 0))
			throw new RuntimeException("Uninitialized query: you must call setQuery() or setMultiQuery() before executing a Query");

		boolean valid = false;

		for (String q : queries) {
			if (q != null) {
				valid = true;
				break;
			}
		}

		if (!valid)
			throw new RuntimeException("Uninitialized query: you must call setQuery() or setMultiQuery() before executing a Query");
		
		try {
			Analyzer analyzer = new StopAnalyzer(LUCENE_STOPWORDS);

			Query q = ((queries.length == 1) ?
				(new MultiFieldQueryParser(fields, analyzer)).parse(filterQuery(queries[0])) :
				MultiFieldQueryParser.parse(filterQueries(queries), fields,
					flags, analyzer));

			fullTextQuery = getFullTextSession().createFullTextQuery(q, modelClazz);
			fullTextQuery.setSort(getLuceneSort(sort));
			fullTextQuery.setFirstResult(getFirstItem());
			fullTextQuery.setMaxResults(getItemsPerPage());

			log.debug(String.format("Executing query: '%s' with sorting in '%s', first result as '%d' and ipp as '%d'", q, sort, getFirstItem(), getItemsPerPage()));
			tx = getFullTextSession().beginTransaction();
			result = fullTextQuery.list();
			tx.commit();
			log.debug(String.format("Query returned %d results", result.size()));
		} catch (ParseException e) {
			log.error("Error parsing multiple queries: " + e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			if ((tx != null) && (tx.isActive()))
				tx.rollback();
			throw new RuntimeException(e);
		}
	}
}
