package org.mvirtual.persistence.hibernate.search.bridge;

import org.mvirtual.persistence.entity.relation.embedded.HeritageSubjectId;
import org.hibernate.search.bridge.TwoWayStringBridge;

/**
 * HeritageSubjectId string bridge class.
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
public class HeritageSubjectIdBridge implements TwoWayStringBridge
{
    @Override
	public String objectToString(Object object) {
		
		if (object instanceof HeritageSubjectId) {
			HeritageSubjectId id = (HeritageSubjectId) object;

			if (id.getIdHeritage() == null || id.getSubject() == null)
				return "";
			
			return (id.getIdHeritage() + "@" + id.getSubject());
		}

		return "";
	}

	public Object stringToObject(String stringValue) {
		String[] returnId = stringValue.split("@", 2);

		if (returnId.length < 2)
			return null;

		return new HeritageSubjectId(Long.valueOf(returnId[0]), returnId[1]);
	}
}
