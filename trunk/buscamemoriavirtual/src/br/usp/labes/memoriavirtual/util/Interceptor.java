/**
 * 
 */
package br.usp.labes.memoriavirtual.util;


import java.text.SimpleDateFormat;

/**
 * @author paul
 *
 */

public class Interceptor {

	public static void filter(String str) throws Exception {
		if (str == null)
			throw new Exception("Null parameter");
	}

	public static void filter(String str, boolean sqlinjection) 
		throws Exception {
		filter(str);
		if (sqlinjection) {
			if (str.indexOf("--") != -1)
				throw new Exception("Starting SQL");
			if (str.indexOf("insert into") != -1)
				throw new Exception("Inserting");
		}
	}

	public static void filter(int var, int start, int end)
		throws Exception {
		if (var < start || var > end)
			throw new Exception("Out of Range");
	}
	
	/**
	   * Answers <code>true</code> if the given number is infinite (i.e., is
	   * a <code>Float</code> or <code>Double</code> containing one of the
	   * predefined constant values representing positive or negative infinity).
	   *
	   * @param number
	   * @return boolean
	   */
	public static boolean isInfinite(Number number) {
	    if (number instanceof Double && ((Double)number).isInfinite())
	      return true;
	    if (number instanceof Float && ((Float)number).isInfinite())
	      return true;
	    return false;
	  }
	
	/**
	   * Answers <code>true</code> if the given number is an instance of
	   * <code>Float</code> or <code>Double</code>.
	   *
	   * @param number
	   * @return boolean
	   */
	 public static boolean isNaN(Number number) {
		    if (number instanceof Double && ((Double)number).isNaN())
		      return true;
		    if (number instanceof Float && ((Float)number).isNaN())
		      return true;
		    return false;
	 }
	 
	public static void filter(int id)
	throws Exception {
		if (isNaN(id) || isInfinite(id))
		      throw new IllegalArgumentException("Argument must not be NaN or infinite.");
	}
	
	public static void filterEmail(String email) 
		throws Exception {
		if (email.indexOf("@") == -1)
			throw new Exception("There is no at :)");
	}
	
	public static void filterDate(String data, String pattern) throws Exception {  
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
			sdf.parse(data);
	}

}
