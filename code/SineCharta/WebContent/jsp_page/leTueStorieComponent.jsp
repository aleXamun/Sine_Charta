
<%@page import="beans.User"%>
<%@page import="beans.Storia"%>
<%@page import="beans.SessioneDiGioco"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Collection"%>


<%
	User utente = (User) session.getAttribute("user");
	Collection<Storia> stList = (Collection<Storia>) session.getAttribute("storieModeratore");

	if (stList != null) {

		if (!stList.isEmpty()) {
			for (Storia st : stList) {

				Set<SessioneDiGioco> listaSessioni = st.getListaSessioni();
	
				System.out.println("NUMERO DI SESSIONI NELLA STORIA CORRENTE: "+ st.getListaSessioni().size());
				
				out.print("<table class=\"table table-dark sTable mt-3\" id=\""+st.getId()+"\">");
				out.print("<thead>");
				out.print("<tr>");
				out.print("<th scope=\"col\">" + st.getTitolo() + "</th>");
				out.print("<th scope=\"col\"></th>");
				out.print("<th scope=\"col\"></th>");
				out.print("</tr>");
				out.print("</thead>");
				out.print("<tbody>");

				for (SessioneDiGioco sdg : listaSessioni) {
					out.print("<tr>");
					out.print("<form method=\"post\" action=\"../GestioneSessioneServlet?action=prendiSessione&idStoria="+st.getId()+"&numero="+sdg.getIdNumeroSessione()+"\">");
					out.print("<td>");
					out.print("Sessione numero: "+sdg.getIdNumeroSessione());
					out.print("</td>");
					out.print("<td> </td>");
					out.print(
							"<td><button type=\"submit\" class=\"btn btn-dark\" style=\"background-color: #212529; border-color: red;\">Gioca</button></td>");
					out.print("</form>");
					out.print("</tr>");
				}
				out.print("<tr>");
				out.print("<td class=\"td-prod\"> Nuova sessione</td>");
				out.print("<td>");
				out.print("<form method=\"post\" action=\"../GestioneSessioneServlet?action=nuovaSessione&idStoria="+st.getId()+"\">");
				out.print(
						"<td><button type=\"submit\" class=\"btn btn-dark\" style=\"background-color: #212529; border-color: red;\">Nuova sessione</button></td>");
				out.print("</form>");
				out.print("</td>");
				out.print("</tbody>");
				out.print("</table>");
			}
%>
<h3>Inizia una nuova avventura</h3>
<table class="table table-dark">
	<tr>
		<td><a href="editorStoria.jsp" class="btn btn-dark">Nuova
				storia</a></td>
	</tr>
</table>


<%
	} else {
			out.print("<h3>Non hai scritto nessuna storia. Clicca <a href=\"editorStoria.jsp\">qui</a> per iniziare.</h3>");
		}

	} else {
%>

<h2>SIAMO SPIACENTI, MA LA FUNZIONE PER DIVENTARE MODERATORI NON �
	PRESENTE.</h2>


<%
	}
%>
