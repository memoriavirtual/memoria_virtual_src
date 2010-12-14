package org.mvirtual.persistence.hibernate.search.bridge;

import org.mvirtual.persistence.entity.relation.embedded.HeritageSubjectId;

import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * HeritageSubjectId field bridge class.
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
public class HeritageSubjectIdClassBridge implements FieldBridge {

    public void set(String name, Object value, Document document,
			LuceneOptions options) {

		if (value instanceof HeritageSubjectId) {

			Field.Store store = options.getStore();
			Field.Index index = options.getIndex();
			Float boost = options.getBoost();

			HeritageSubjectId id = (HeritageSubjectId) value;

			Long idHeritage = id.getIdHeritage();
			String subject = id.getSubject();

			if (idHeritage == null || subject == null)
				return;

			String fieldValue = idHeritage + " " + subject;

			Field field1 = new Field(name, fieldValue, store, index);

			if (boost != null)
				field1.setBoost(boost);

			document.add(field1);

			Field field2 = new Field("idHeritage", idHeritage.toString(), store, index);
			document.add(field2);

			Field field3 = new Field("subject", subject, store, index);
			document.add(field3);
		}
    }
}
