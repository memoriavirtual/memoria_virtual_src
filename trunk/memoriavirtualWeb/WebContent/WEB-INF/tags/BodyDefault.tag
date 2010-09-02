<%-- 
    Document   : defaultPage
    Created on : 01.07.2009, 22:15:59
    Author     : Fabricio Zancanella
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="templates" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@tag description="Default Page" pageEncoding="UTF-8" %>

<%@attribute name="mainHeader" required="false" rtexprvalue="true" %>
<%@attribute name="topMenu" required="false" rtexprvalue="true" %>
<%@attribute name="sidebar" required="false" rtexprvalue="true" %>
<%@attribute name="footer" required="false" rtexprvalue="true" %>

    <div id="container">

        <div id="header">
            <div id="logoContainer" >
                <div id="logo" >
                    <p><img src="/memoriavirtual/images/banner.png" alt="Mem&oacute;ria Virtual" /></p>
                </div>
            </div>
        </div>

        <div id="topMenu">
            ${topMenu}
        </div>

        <div id="mainContent">
            <div id="sidebar">
                ${sidebar}
            </div>

            <div id="content">
                <jsp:doBody />
            </div>

        </div>

        <div id="footer">
            ${footer}
        </div>
    </div>
