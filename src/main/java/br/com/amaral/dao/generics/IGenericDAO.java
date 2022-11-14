package br.com.amaral.dao.generics;

import java.io.Serializable;
import java.util.Collection;

import br.com.amaral.domain.Persistent;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;
import br.com.amaral.exceptions.MoreThanOneRecordException;
import br.com.amaral.exceptions.TableException;

/**
 * @author leandro.amaral
 *
 *         Interface genérica para métodos de CRUD(Create, Read, Update and
 *         Delete)
 */
public interface IGenericDAO<T extends Persistent, E extends Serializable> {

	/**
	 * Método para cadastrar novos registro no banco de dados
	 *
	 * @param entity a ser cadastrado
	 * @return retorna verdadeiro para cadastrado e falso para não cadastrado
	 */
	public T save(T entity) throws KeyTypeNotFoundException, DAOException;

	/**
	 * Método para excluir um registro do banco de dados
	 *
	 * @param valor chave única do dado a ser excluído
	 */
	public void delete(T entity) throws DAOException;

	/**
	 * Método para alterar um registro no bando de dados.
	 *
	 * @param entity a ser atualizado
	 */
	public T update(T entity) throws KeyTypeNotFoundException, DAOException;

	/**
	 * Método para consultar um registro no banco de dados
	 *
	 * @param valor chave única do dado a ser consultado
	 * @return
	 */
	public T search(E value) throws MoreThanOneRecordException, TableException, DAOException;

	/**
	 * Método que irá retornar todos os registros do banco de dados de uma
	 * determinado dado ou tabela
	 *
	 * @return Registros encontrados
	 */
	public Collection<T> searchAll() throws DAOException;
}