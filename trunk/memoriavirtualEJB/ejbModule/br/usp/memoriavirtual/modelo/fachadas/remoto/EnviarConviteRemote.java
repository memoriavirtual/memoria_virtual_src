package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface EnviarConviteRemote {

    public String enviarConvite(String emails, String mensagem, String validade, String instituicao, String nivelAcesso);

    public List<Instituicao> getInstituicoesAutorizadas(Usuario usuario);
}
