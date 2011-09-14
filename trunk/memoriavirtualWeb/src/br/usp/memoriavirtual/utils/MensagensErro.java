package br.usp.memoriavirtual.utils;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensagensErro {
	
	private static String bundleName = "mensagens";

	
	public static boolean getErrorMessage(String msg, String target) {
		return getMessageFromResourceBundle(msg, target,
				FacesMessage.SEVERITY_ERROR);
	}
	
	public static boolean getSucessMessage(String msg, String target) {
		return getMessageFromResourceBundle(msg, target,
				FacesMessage.SEVERITY_INFO);
	}
	
	public static boolean getWarningMessage(String msg, String target) {
		return getMessageFromResourceBundle(msg, target,
				FacesMessage.SEVERITY_WARN);
	}

	 private static boolean getMessageFromResourceBundle(String msg,
			String target, FacesMessage.Severity severity) {

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);
		String message = bundle.getString(msg);

		FacesContext.getCurrentInstance().addMessage(target,
				new FacesMessage(severity, message, null));

		return true;
	}

}
