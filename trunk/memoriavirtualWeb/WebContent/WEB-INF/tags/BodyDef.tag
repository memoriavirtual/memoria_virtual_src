<%-- 
    Document   : defaultPage
    Created on : 01.07.2009, 22:15:59
    Author     : Fabricio Zancanella
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="templates" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@tag description="Default Page" pageEncoding="UTF-8" %>

<%@attribute name="logo" required="false" rtexprvalue="true" %>
<%@attribute name="mainHeader" required="false" rtexprvalue="true" %>
<%@attribute name="topMenu" required="false" rtexprvalue="true" %>
<%@attribute name="sidebar" required="false" rtexprvalue="true" %>
<%@attribute name="footer" required="false" rtexprvalue="true" %>

    <div id="sitemain">

          <div id="Logosp">
            <div id="Logo">
              <div class="lb"><div class="lr"><div class="lt"><div class="ll"><div class="tl"><div class="tr"><div class="bl"><div class="br">
                <div style="text-align:center">
                    <p><img src="${logo}" style="border:0" hspace="20" alt="" /></p>
                </div>
              </div></div></div></div></div></div></div></div>
            </div>
          </div>



    </div>
