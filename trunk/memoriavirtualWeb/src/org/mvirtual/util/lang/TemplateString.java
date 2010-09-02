package org.mvirtual.util.lang;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * @author Kiyoshi Murata
 */
public class TemplateString
{
	private String template;
	private String substitutionOnMissing;

	public TemplateString() {
		template = "";
		substitutionOnMissing = "?";
	}

	public TemplateString(String t) {
		template = t;
		substitutionOnMissing = "?";
	}

	/**
	 * Constructor.
	 * @param t Template string.
	 * @param s Default substitution string when no substitution string is given.
	 */
	public TemplateString(String t, String s) {
		template = t;
		substitutionOnMissing = s;
	}

	public String substitute(String[] keys, String[] values) {
		int i = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		for (String key : keys) {
			String value;
			if (i > values.length) {
				value = "";
			} else {
				value = values[i];
			}
			map.put(key, value);
			i++;
		}
		return substitute(map);
	}

	public String substitute(Map<String, String> substitutions) {
		Pattern p = Pattern.compile("\\$\\{(\\w+)\\}");
		Matcher m = p.matcher(template);
		StringBuffer result = new StringBuffer();
		int s = 0;

		while (m.find())
		{
			String sub = substitutions.get(m.group(1));
			
			if (sub == null) {
				sub = substitutionOnMissing;
			}
			
			result.append(template, s, m.start());
			result.append(sub);

			s = m.end();
		}

		result.append(template, s, template.length());
		return result.toString();
	}

        @Override
	public String toString() {
		return template;
	}
}
