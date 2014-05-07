package br.usp.memoriavirtual.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

public class MVModeloEmailParser {

	public MVModeloEmailParser() {

	}

	public String getMensagem(Map<String, String> tags,
			MVModeloEmailTemplates template) throws ModeloException {
		try {
			String email = lerArquivo(template.toString(),
					Charset.defaultCharset());

			Iterator<Entry<String, String>> it = tags.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> par = (Map.Entry<String, String>) it.next();
				email = email.replace(par.getKey(), par.getValue());
			}

			return email;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	static String lerArquivo(String caminho, Charset encoding)
			throws IOException {
		
		byte[] encoded = Files.readAllBytes(Paths.get(new File(caminho).getAbsolutePath()));
		return new String(encoded, encoding);
	}

}
