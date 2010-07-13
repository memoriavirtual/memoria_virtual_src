package org.mvirtual.persistence.hibernate.search.bridge;

import org.mvirtual.persistence.entity.relation.embedded.AuthorityHeritageId;

import org.hibernate.search.bridge.TwoWayStringBridge;

/**
 * AuthorityHeritageId string bridge class.
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
public class AuthorityHeritageIdBridge implements TwoWayStringBridge
{

	public String objectToString(Object object)
	{
		if (object instanceof AuthorityHeritageId) {
			AuthorityHeritageId id = (AuthorityHeritageId) object;

			if (id.getIdHeritage() == null || id.getIdAuthority() == null || id.getAuthorShipType() == null)
				return "";

			return (id.getIdHeritage() + "@" + id.getIdAuthority() + "@" + id.getAuthorShipType());
		}

		return "";
	}

	public Object stringToObject(String stringValue)
	{
		String[] returnId = stringValue.split("@", 3);

		if (returnId.length < 3)
			return null;

		return new AuthorityHeritageId(Long.valueOf(returnId[0]), Long.valueOf(returnId[1]), returnId[2]);
	}
}
