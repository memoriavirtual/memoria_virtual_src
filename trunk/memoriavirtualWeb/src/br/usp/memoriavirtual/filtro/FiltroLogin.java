package br.usp.memoriavirtual.filtro;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FiltroLogin
 */
@WebFilter("/FiltroLogin")
public class FiltroLogin implements Filter ,  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2872604550129629756L;

	/**
	 * Default constructor.
	 */
	public FiltroLogin() {
		
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getRequestURL().toString();

		HttpSession session = req.getSession();

		Object user = null;
		user = req.getSession().getAttribute("usuario");
		if (user == null) {

			session.setAttribute("url", url);

			resp.sendRedirect(req.getContextPath() + "/login.jsf");
			return;
		} else {

			String urlDireta = null;

			if (session.getAttribute("url") != null) {
				urlDireta = session.getAttribute("url").toString();
				session.removeAttribute("url");

				resp.sendRedirect(urlDireta);
				return;
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
