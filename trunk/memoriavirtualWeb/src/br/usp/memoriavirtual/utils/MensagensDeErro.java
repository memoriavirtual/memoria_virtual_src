package br.usp.memoriavirtual.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensagensDeErro {

	private static String bundleName = MVControleMemoriaVirtual.bundleName;

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
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		String message = bundle.getString(msg);

		FacesContext.getCurrentInstance().addMessage(target,
				new FacesMessage(severity, message, null));

		return true;
	}
	
	public static boolean getErrorMessage(String msg, Object[] argumentos,
			String target) {
		return getMessageFromResourceBundle(msg, argumentos,target,
				FacesMessage.SEVERITY_ERROR);
	}

	public static boolean getSucessMessage(String msg, Object[] argumentos,
			String target) {
		return getMessageFromResourceBundle(msg, argumentos,target,
				FacesMessage.SEVERITY_INFO);
	}

	public static boolean getWarningMessage(String msg, Object[] argumentos,
			String target) {
		return getMessageFromResourceBundle(msg, argumentos, target,
				FacesMessage.SEVERITY_WARN);
	}

	private static boolean getMessageFromResourceBundle(String msg,
			Object[] argumentos, String target, FacesMessage.Severity severity) {

		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		
		String message = bundle.getString(msg);
		String messageFinal = MessageFormat.format(message, argumentos);

		FacesContext.getCurrentInstance().addMessage(target,
				new FacesMessage(severity, messageFinal, null));

		return true;
	}
}
