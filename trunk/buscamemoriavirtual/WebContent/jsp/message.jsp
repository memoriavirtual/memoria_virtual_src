<%--
    Document   : message
    Created on : Mar 24, 2010, 12:42:03 AM
    Author     : paul
--%>
<%@page import="java.util.*, java.sql.*, br.usp.labes.memoriavirtual.database.*, br.usp.labes.memoriavirtual.busca.*" %>

<jsp:include page="../config/header.jsp" />

   <%
	ResourceBundle r = ResourceBundle.getBundle(
		"br.usp.labes.memoriavirtual.busca.Messages", request.getLocale());
	%>

                                <%
									int msg = Integer.parseInt(request.getParameter("id"));
								
									switch (msg) {
										case 0: {
											out.print(r.getString("USER_SEARCH_OK"));
											break;
										}
										case 1: {
											out.print(r.getString("USER_SEARCH_ERROR"));
											break;
										}
										case 2: {
											out.print(r.getString("USER_UNDERCONSTRUCTION_OK"));
											break;
										}
										case 3: {
											out.print(r.getString("USER_UNDERCONSTRUCTION_ERROR"));
											break;
										}
								
									}
								
								%>                                

<jsp:include page="../config/footer.jsp" />