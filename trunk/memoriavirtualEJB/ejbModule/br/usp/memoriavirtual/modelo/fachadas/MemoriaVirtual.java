package br.usp.memoriavirtual.modelo.fachadas;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

/**
 * EJB Sem estado e singleton que contém métodos comuns ao sistema todo.
 */
@Singleton
public class MemoriaVirtual implements MemoriaVirtualRemote {

	private static InetAddress enderecoServidor = null;
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;


	/**
	 * Default constructor.
	 */
	public MemoriaVirtual() {

	}

	/**
	 * Retorna o endereço físico do servidor.
	 * 
	 * @throws IOException
	 * 
	 */
	public InetAddress getEnderecoServidor() throws IOException {

		if (enderecoServidor == null) {
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();

			while (interfaces.hasMoreElements()) {
				NetworkInterface interfaceRede = (NetworkInterface) interfaces
						.nextElement();
				if (interfaceRede.isUp()) {
					Enumeration<InetAddress> enderecosRede = interfaceRede
							.getInetAddresses();
					while (enderecosRede.hasMoreElements()) {
						InetAddress enderecoRede = (InetAddress) enderecosRede
								.nextElement();
						if (enderecoRede.isReachable(10)
								&& !enderecoRede.isLoopbackAddress()
								&& !enderecoRede.isAnyLocalAddress()) {
							enderecoServidor = enderecoRede;
							return enderecoServidor;
						}
					}
				}
			}
		}
		return enderecoServidor;
	}
	
	public boolean disponibilidadeEmail(String email) {

		Query query = entityManager
				.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		query.setParameter("email", email);

		try {
			query.getSingleResult();
			return false;
		} catch (NoResultException e) {

		}
		return true;
	}

	public boolean validarEmail(String email) {

		String regexp = "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
				+ "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
				+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches())
			return false;

		return true;
	}

}
