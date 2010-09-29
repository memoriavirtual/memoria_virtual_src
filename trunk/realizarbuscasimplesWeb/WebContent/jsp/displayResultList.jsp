<%--
    Document   : displayResultsList
    Created on : Mar 24, 2010, 12:42:03 AM
    Author     : paul
--%>
<%@page import="java.util.*, java.sql.*" %>
<%@page import="br.usp.labes.memoriavirtual.modelo.fachada.BemPatrimonial"%>
<%@page import="br.usp.labes.memoriavirtual.modelo.fachada.RealizarBuscaSimples"%>

<jsp:include page="../config/header.jsp" />

                                <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                  <!-- B.2 MAIN CONTENT -->
                                  <div class="main-content">

                                    <!-- Pagetitle -->
	                                <br />
	                                <form action="jsp/displayResultList.jsp">
	                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                <input style="WIDTH: 60%; HEIGHT: 22px" size=25 name=buscar value="" align="center">
	                                <br />
	                                <br />
	                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                    <input type="submit" value="Pesquisa na Memorial Virtual" name="pesquisa" style="WIDTH: 28%; HEIGHT: 24px" size=25>
	                                </form>
	                                <br />
	                                
                                    <h1 class="pagetitle">:: Resultado da Sua Buscar </h1>                                     
                                     
                     				<%
                     				
                     				List<BemPatrimonial> list = new ArrayList<BemPatrimonial>();
                     				BemPatrimonial bempatrimonial = (BemPatrimonial) request.getAttribute("busca");
                     				list.add(bempatrimonial);
                     				
                     				Iterator<BemPatrimonial> i = list.iterator();  
                     				while (i.hasNext()) {  
                     					BemPatrimonial bemPatrimonial = i.next();
									%>
									<!-- Content unit - One column -->	
									<hr class="clear-contentunit" />                                    		
                                    <div class="column1-unit">
                                      <p><span class="description"><img src="images/museu.JPG" width="100" height="50" />
                                         	
                                         	<%
											out.println("<br />");
                                         	out.println(bempatrimonial.getId());
                                         	out.println(bempatrimonial.getId_institution());
                                         	out.println(bempatrimonial.getTitle());
                                         	out.println(bempatrimonial.getAccessconditions());
                                         	out.println(bempatrimonial.getAcquisitioncurrentowner());
                                         	out.println(bempatrimonial.getAcquisitiondate());
                                         	out.println(bempatrimonial.getAcquisitionorigin());
                                         	out.println(bempatrimonial.getAcquisitiontype());
                                         	out.println(bempatrimonial.getAcquisitionvalue());
                                         	out.println(bempatrimonial.getAlternativetitle());
                                         	out.println(bempatrimonial.getCollection());
                                         	out.println(bempatrimonial.getComplementtitle());
                                         	out.println(bempatrimonial.getCondition());
                                         	out.println(bempatrimonial.getConditionnotes());
                                         	out.println(bempatrimonial.getContent());
                                         	out.println(bempatrimonial.getControlnumber());
                                         	out.println(bempatrimonial.getDate());
                                         	out.println(bempatrimonial.getDimensions());
                                         	out.println(bempatrimonial.getAditionnumber());
                                         	out.println(bempatrimonial.getFindingaid());
                                         	out.println(bempatrimonial.getGps_latitude());
                                         	out.println(bempatrimonial.getGps_longitude());
                                         	out.println(bempatrimonial.getHeritageprotectioninstitution());
                                         	out.println(bempatrimonial.getHistoric());
                                         	out.println(bempatrimonial.getLegislation());
                                         	out.println(bempatrimonial.getLocal());
                                         	out.println(bempatrimonial.getNote());
                                         	out.println(bempatrimonial.getOriginaltitle());
                                         	out.println(bempatrimonial.getOtherresponsibilities());
                                         	out.println(bempatrimonial.getPhysicalfeatures());
                                         	out.println(bempatrimonial.getPhysicallocation());
                                         	out.println(bempatrimonial.getProtection());
                                         	out.println(bempatrimonial.getRegistrynumber());
                                         	out.println(bempatrimonial.getReissuenumber());
                                         	out.println(bempatrimonial.getReproductionconditions());
                                         	out.println(bempatrimonial.getReviewed());
                                         	out.println(bempatrimonial.getSituation());
                                         	out.println(bempatrimonial.getSupport());
                                         	out.println(bempatrimonial.getUsage());
												
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
