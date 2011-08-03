package br.usp.memoriavirtual.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

public class CadastrarUsuario extends HttpServlet {

	EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("memoriavirtual");
	EntityManager em = emf.createEntityManager();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String validacao = request.getParameter("Validacao");
		String email = request.getParameter("email");

		Query query = this.em
				.createQuery("SELECT u FROM Usuario u WHERE u.id = :usuario");
		query.setParameter("usuario", validacao);

		Usuario usuarioAutenticado = null;

		try {
			usuarioAutenticado = (Usuario) query.getSingleResult();
			if (!usuarioAutenticado.isAtivo()) {
				request.getSession()
						.setAttribute("usuario", usuarioAutenticado);
			} else {
				usuarioAutenticado = null;
			}
		} catch (NoResultException e) {
			usuarioAutenticado = null;
			e.printStackTrace();
		}

		if (usuarioAutenticado != null) {
			response.sendRedirect("restrito/cadastrarusuario.jsf?validacao="
					+ validacao + "&email=" + email);
			// Caso queria fazer por foward para não mudar a url usar código
			// abaixo:
			// javax.servlet.RequestDispatcher dispatcher =
			// request.getRequestDispatcher("cadastrarusuario.jsf?validacao="+validacao+"&email="+email);
			// dispatcher.forward(request, response);
		} else {
			response.sendRedirect("conviteinvalido.jsf");
		}
	}
}
