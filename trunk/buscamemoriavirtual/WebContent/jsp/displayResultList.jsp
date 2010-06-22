<%--
    Document   : displayResultsList
    Created on : Mar 24, 2010, 12:42:03 AM
    Author     : paul
--%>
<%@page import="java.util.*, java.sql.*" %>
<jsp:include page="../config/header.jsp" />

                                <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                  <!-- B.2 MAIN CONTENT -->
                                  <div class="main-content">

                                    <!-- Pagetitle -->
	                                <br />
	                                <form action="jsp/displayResultList.jsp">
	                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                <input style="WIDTH: 60%; HEIGHT: 22px" size=25 name=buscar value="" align="center">
	                                <br /><br />
	                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                    <input type="submit" value="Pesquisa na Memorial Virtual" name="pesquisa" style="WIDTH: 28%; HEIGHT: 24px" size=25>
	                                </form>
	                                <br />
	                                
                                    <h1 class="pagetitle">:: Resultado da Sua Buscar </h1>                                     
                                     
                     				<%
                                         	//ResultSet rs = (ResultSet) request.getAttribute("busca");
											String L_mensagem = (String) request.getAttribute("Mensagem");
											int count = 0;
											
											while(count < 20){
									%>
									<!-- Content unit - One column -->	
									<hr class="clear-contentunit" />                                    		
                                    <div class="column1-unit">
                                      <p><span class="description"><img src="images/museu.JPG" width="100" height="50" />
                                         	
                                         	<%
												out.println("<br />");
												out.println(L_mensagem);
												count++;
											%>
											<br />
                                        <a href="../jsp/displayContent.jsp">Leia Mais.. </a></p>
                                    </div>
                                    <hr class="clear-contentunit" />
                                    <!-- Content unit - One column -->
									<%
									}
											
									%>
                                    
                                    <!-- Content unit - One column -->
                                    <hr class="clear-contentunit" />
                                    <!-- Content unit - One column -->
                                    <div class="column1-unit">
                                        <p align="center"><span class="description">
                                              <br />
                                              <a href="#"><img src="images/previous_arrow.png" width="20" height="20" /></a>
                                              &nbsp;&nbsp;&nbsp;&nbsp;
                                              <a href="#"><font size="5" color="red">1</font></a>
                                              <a href="#"><font size="5" color="red">2</font></a>
                                              <a href="#"><font size="5" color="red">3</font></a>
                                              <a href="#"><font size="5" color="red">4</font></a>
                                              <a href="#"><font size="5" color="red">5</font></a>
                                              <a href="#"><font size="5" color="red">6</font></a>
                                              <a href="#"><font size="5" color="red">7</font></a>
                                              <a href="#"><font size="5" color="red">8</font></a>
                                              <a href="#"><font size="5" color="red">9</font></a>
                                              <a href="#"><font size="5" color="red">10</font></a>
                                              &nbsp;&nbsp;&nbsp;&nbsp;
                                              <a href="#"><img src="images/Next_arrow.png" width="20" height="20" /></a>
                                              
                                      </p>
                                    </div>
                                    <!-- Content unit - One column -->

                                  </div>
                                </table>

<jsp:include page="../config/footer.jsp" />
