package org.mvirtual.persistence.hibernate.search.bridge;

import org.mvirtual.persistence.entity.relation.embedded.AuthorityHeritageId;

import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * AuthorityHeritageId field bridge class.
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
public class AuthorityHeritageIdClassBridge implements FieldBridge {

    @Override
    public void set(String name, Object value, Document document,
			LuceneOptions options) {
		
		if (value instanceof AuthorityHeritageId) {

			Field.Store store = options.getStore();
			Field.Index index = options.getIndex();
			Float boost = options.getBoost();

			AuthorityHeritageId id = (AuthorityHeritageId) value;

			Long idHeritage = id.getIdHeritage();
			Long idAuthority = id.getIdAuthority();
			String authorshiptype = id.getAuthorShipType();

			if (idHeritage == null || idAuthority == null || authorshiptype == null)
				return;

			String fieldValue = idHeritage + " " + idAuthority + " " + authorshiptype;

			Field field1 = new Field(name, fieldValue, store, index);

			if (boost != null)
				field1.setBoost(boost);

			document.add(field1);

			Field field2 = new Field("idHeritage", idHeritage.toString(), store, index);
			document.add(field2);

			Field field3 = new Field("idAuthority", idAuthority.toString(), store, index);
			document.add(field3);

			Field field4 = new Field("authorshiptype", authorshiptype, store, index);
			document.add(field4);
		}
    }
}
