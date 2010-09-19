<%-- 
    Document   : defaultPage
    Created on : 01.07.2009, 22:15:59
    Author     : Fabricio Zancanella
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="templates" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@tag description="Default Page" pageEncoding="UTF-8" %>

<%@attribute name="logo"       required="false" rtexprvalue="true" %>
<%@attribute name="mainHeader" required="false" rtexprvalue="true" %>
<%@attribute name="topMenu"    required="false" rtexprvalue="true" %>
<%@attribute name="sidebar"    required="false" rtexprvalue="true" %>
<%@attribute name="footer"     required="false" rtexprvalue="true" %>

    <div id="sitemain">

    <div id="logosp" >
        ${logo}
    </div>

    <div id="topMenusp">
        ${topMenu}
    </div>

    <div id="groupsp">
        <div id="group">
            <div id="sidebarArea">
                ${sidebar}
            </div>

            <div id="content">
                <jsp:doBody />
            </div>
        </div>

        </div>

        <div id="footer">
            ${footer}
        </div>
    </div>
