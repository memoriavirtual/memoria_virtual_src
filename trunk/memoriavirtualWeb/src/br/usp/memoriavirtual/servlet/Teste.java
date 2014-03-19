package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/huhu")
public class Teste extends HttpServlet {

	private static final long serialVersionUID = -3455546327228843100L;

	public Teste() {
		super();
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	response.setContentType("text/html");
 
        // Set response body content. response body is returned as Ajax Response Text
        PrintWriter writer = response.getWriter();
        writer.write("Hello World!!");    // "Hello World!!" is returned as Ajax Response Text in this example
        writer.flush();
        writer.close();
    }

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter printout = response.getWriter();

		String JObject = "{'id':'joe', 'text':'ahfskdjfhskldfhslkdjfs'}";

		printout.print(JObject);
		printout.flush();
		// Or
		// printout.write(JObject.toString());
	}

}
