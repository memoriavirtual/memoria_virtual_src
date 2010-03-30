package org.mvirtual.persistence;

public interface PersistenceStatics
{
	/**
	 *
	 */
	public static final String LUCENE_STRIP_FROM_QUERY_REGEX = "[^A-Za-z0-9áéíóúâêîôûàèìòùäëïöüçãõñ�?É�?ÓÚÂÊÎÔÛÀÈÌÒÙÄË�?ÖÜÇÃÕÑ\" ]";

	/**
	 * Default entity fields to search for.
	 */
	public static final String[] LUCENE_DEFAULT_FIELDS = {"institution.name", "title", "originaltitle", 
			"alternativetitle", "collection", "physicallocation", "complementtitle", "situation", "local", "date", 
			"editionnumber", "reissuenumber", "otherresponsibilities", "content", "note", "dimensions", "physicalfeatures", 
			"support", "condition", "conditionnotes", "accessconditions", "reproductionconditions", "usage", "protection", 
			"heritageprotectioninstitution", "legislation", "acquisitiontype", "acquisitionorigin", "acquisitiondate", 
			"acquisitioncurrentowner", "historic", "authorityHeritages.function", "authorityHeritages.id", 
			"authorityHeritages.id.authorshiptype", "authorityHeritages.authority.name", 
			"authorityHeritages.authority.nickname", "authorityHeritages.authority.birthdate", 
			"authorityHeritages.authority.deathdate", "descriptors.descriptor", 
			"heritageSubject.heritageSubjectId.subject", "heritagetypes.heritagetype", "interventions.responsible",
			"interventions.description", "interventions.date"};
	
	/**
	 * Common stop words (words filtered from queries) for English, Brazilian Portuguese and Spanish.
	 */
	public static final String[] LUCENE_STOPWORDS = {"a", "al", "all", "also", "among", "an", "and", "ante", "any", "ao", "aos", "aquela",
			"aquelas", "aquele", "aqueles", "aquem", "aqui", "are", "as", "at", "ate", "bajo", "by", "cada", "causa", "causas", "certa",
			"certas", "certo", "certos", "com", "como", "con", "da", "das", "de", "dele", "deles", "demais", "dentre", "depois",
			"desde", "dessa", "dessas", "desse", "desses", "desta", "destas", "deste", "destes", "diante", "difira", "disso",
			"disto", "dito", "ditos", "do", "dos", "e", "each", "ed", "el", "ela", "elas", "ele", "eles", "em", "en", "entanto",
			"entao", "entre", "es", "esa", "ese", "eso", "essa", "essas", "esse", "esses", "esta", "estas", "este", "estes",
			"esto", "estos", "every", "for", "from", "he", "if", "in", "into", "is", "isso", "isto", "it", "its", "la", "las",
			"lhe", "lhes", "lo", "los", "mais", "many", "mas", "me", "mesmo", "meu", "minha", "more", "most", "muita", "muitas",
			"muito", "muitos", "my", "na", "nada", "nao", "nas", "nessa", "nessas", "nesta", "nestas", "neste", "nestes", "no",
			"nos", "nossa", "nossas", "nosso", "nossos", "o", "of", "or", "os", "other", "others", "otra", "otro", "ou", "our",
			"outra", "outras", "outro", "outros", "para", "pela", "pelas", "pero", "pois", "por", "porem", "porisso", "porque",
			"quaisquer", "qualquer", "quando", "quanto", "que", "se", "seu", "seus", "si", "sim", "sin", "sobre", "some", "sua",
			"suas", "tambem", "tambien", "tanto", "than", "that", "the", "then", "their", "there", "these", "this", "to", "toda",
			"todas", "todo", "todos", "torna", "uma", "umas", "un", "una", "unas", "unos", "uns", "upon", "was", "we", "what",
			"were", "when", "which", "while", "whit", "why", "with", "y"};
	
	/**
	 * Default sort field in queries.
	 */
	public static final String LUCENE_DEFAULT_SORTFIELD = "relevance";


	/**
	 * Default amount of items to include in paginated searches.
	 */
	public static final int SEARCH_DEFAULT_ITEMS_PER_PAGE = 15;
}
