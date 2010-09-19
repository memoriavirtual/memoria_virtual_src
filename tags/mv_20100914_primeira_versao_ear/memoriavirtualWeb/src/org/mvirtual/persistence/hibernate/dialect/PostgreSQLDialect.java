package org.mvirtual.persistence.hibernate.dialect;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgreSQLDialect
	extends org.hibernate.dialect.PostgreSQLDialect
{
	private final Logger log = LoggerFactory.getLogger(org.hibernate.dialect.MySQLDialect.class);

        @Override
	public String getIdentitySelectString(String table, String column, int type) {

		table = StringUtils.strip(table, "\"");
		column = StringUtils.strip(column, "\"");

		return new StringBuffer().append("select currval('")
			.append(table)
			.append('_')
			.append(column)
			.append("_seq')")
			.toString();
	}
}
