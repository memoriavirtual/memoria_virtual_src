package br.usp.memoriavirtual.modelo.fachadas;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

/**
 * EJB Sem estado e singleton que contém métodos comuns ao sistema todo.
 */
@Singleton
@LocalBean
public class MemoriaVirtual implements MemoriaVirtualRemote {

    private static InetAddress enderecoServidor = null;

    /**
     * Default constructor.
     */
    public MemoriaVirtual() {

    }

    /**
     * Retorna o endereço físico do servidor.
     * @throws IOException
     * 
     */
    public InetAddress getEnderecoServidor() throws IOException {

	if (enderecoServidor == null) {
	    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

	    while (interfaces.hasMoreElements()) {
		NetworkInterface interfaceRede = (NetworkInterface) interfaces.nextElement();
		if (interfaceRede.isUp()) {
		    Enumeration<InetAddress> enderecosRede = interfaceRede.getInetAddresses();
		    while (enderecosRede.hasMoreElements()) {
			InetAddress enderecoRede = (InetAddress) enderecosRede.nextElement();
			if (enderecoRede.isReachable(10) && !enderecoRede.isLoopbackAddress()
				&& !enderecoRede.isAnyLocalAddress()) {
			    System.out.println("Passou aqui");
			    enderecoServidor = enderecoRede;
			    return enderecoServidor;
			}
		    }
		}
	    }
	}
	return enderecoServidor;
    }

}
