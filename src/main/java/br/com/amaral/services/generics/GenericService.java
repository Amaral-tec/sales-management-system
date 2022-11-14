package br.com.amaral.services.generics;

import java.io.Serializable;
import java.util.Collection;

import br.com.amaral.dao.generics.IGenericDAO;
import br.com.amaral.domain.Persistent;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;
import br.com.amaral.exceptions.MoreThanOneRecordException;
import br.com.amaral.exceptions.TableException;

/**
 * @author leandro.amaral
 *
 */
public abstract class GenericService<T extends Persistent, E extends Serializable> implements IGenericService<T, E> {

	protected IGenericDAO<T, E> dao;

	public GenericService(IGenericDAO<T, E> dao) {
		this.dao = dao;
	}

	@Override
	public T save(T entity) throws KeyTypeNotFoundException, DAOException {
		return this.dao.save(entity);
	}

	@Override
	public void delete(T entity) throws DAOException {
		this.dao.delete(entity);
	}
	
	@Override
	public T update(T entity) throws KeyTypeNotFoundException, DAOException {
		return this.dao.update(entity);
	}

	@Override
	public T search(E value) throws DAOException {
		try {
			return this.dao.search(value);
		} catch (MoreThanOneRecordException | TableException e) {
			// TODO Auto-generated catch block
			//TODO levantar um erro genérico
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<T> searchAll() throws DAOException {
		return this.dao.searchAll();
	}

}