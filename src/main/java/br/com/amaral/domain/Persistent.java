package br.com.amaral.domain;

/**
 * @author leandro.amaral
 *
 *         Classe que representa todas as entidades ou objetos da aplicação que
 *         seram salvas no banco de dados
 */
public interface Persistent {

	public Long getId();
	
	public void setId(Long id);
}