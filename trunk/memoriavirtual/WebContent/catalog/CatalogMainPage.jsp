<%--
    Document   : MainPage
    Created on : 16.06.2009, 19:33:36
    Author     : Fabricio Zancanella and Elisa Yumi Nakagawa
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/templates/Header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
 "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
 <s:i18n name="mvirtual/catalog/catalog">
     <html>
         <head>
             <templates:HeadDefault />
             <title>Memória Virtual</title>
         </head>

         <body>

             <div id="sitemain"> <!-- Site main division -->
                 <%@include file="/templates/logo.jsp" %>

                 <div id="topmenusp">
                     <%@include file="/catalog/TopMenu.jsp" %>
                 </div> <%-- topmenusp --%>

                 <div id="groupsp">
                     <div id="group">

                         <div style="text-align:center; font-family: verdana;font-size: medium;padding:70px;">
                                
                             <p>
                                 Bem vindo ao Sistema de Catalogação de bens patrimoniais do Memória Virtual. Use o menu
                                 acima para acessar o sistema.
                             </p>

<!--
                                 <div class="boxsp">
                                     <div class="box">
                                         <ul>
                                             <li>
                                                 <a href="<s:url namespace="/heritage" action="RenderHeritagePage" />" >
                                                    <s:text name="mainContent.heritage" />
                                                 </a>
                                             </li>
                                             
                                             <li>
                                                 <a href="<s:url namespace="/heritage" action="Users" />" >
                                                    <s:text name="mainContent.userEnrollment" />
                                                 </a>
                                             </li>

                                             <li>
                                                 <a href="<s:url namespace="/heritage" action="Institutions" />" >
                                                    <s:text name="Instituição" />
                                                 </a>
                                             </li>


                                             <li>
                                                 <a href="<s:url namespace="/heritage" action="Authorities" />" >
                                                    <s:text name="Autores" />
                                                 </a>
                                             </li>

                                             <li>
                                                 <a href="<s:url namespace="/heritage" action="Descriptors" />" >
                                                    <s:text name="Descritores" />
                                                 </a>
                                             </li>
                                         </ul>
                                     </div> <%-- box --%>
                                 </div> <%-- boxsp --%>
-->

                         </div> <%-- maincontentsp --%>

                     </div> <%-- group --%>
                 </div> <%-- groupsp --%>


                 <div id="footersp">
                     <%@include file="/Footer.jsp" %>
                 </div> <%-- Footersp --%>

             </div> <!-- Site Main Division -->
         </body>
     </html>
</s:i18n>
