package br.com.amaral.dao.generics;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.amaral.domain.Persistent;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;
import br.com.amaral.exceptions.MoreThanOneRecordException;
import br.com.amaral.exceptions.TableException;

/**
 * @author leandro.amaral
 *
 */
public class GenericDAO <T extends Persistent, E extends Serializable> implements IGenericDAO <T,E> {

	protected EntityManagerFactory entityManagerFactory;
	
	protected EntityManager entityManager;
	
	private Class<T> persistentClass;
	
	public GenericDAO(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}
	
	@Override
	public T save(T entity) throws KeyTypeNotFoundException, DAOException {
		openConnection();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		closeConnection();
		return entity;
	}

	@Override
	public void delete(T entity) throws DAOException {
		openConnection();
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
		closeConnection();
	}

	@Override
	public T update(T entity) throws KeyTypeNotFoundException, DAOException {
		openConnection();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		closeConnection();
		return entity;
	}

	@Override
	public T search(E value) throws MoreThanOneRecordException, TableException, DAOException {
		openConnection();
		T entity = entityManager.find(this.persistentClass, value);
		entityManager.getTransaction().commit();
		closeConnection();
		return entity;
	}

	@Override
	public Collection<T> searchAll() throws DAOException {
		openConnection();
		List<T> list = 
				entityManager.createQuery(getSelectSql(), this.persistentClass).getResultList();
		closeConnection();
		return list;
	}
	
	protected void openConnection() {
		entityManagerFactory = 
				Persistence.createEntityManagerFactory("JPA");
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}
	
	protected void closeConnection() {
		entityManager.close();
		entityManagerFactory.close();
	}
	
	private String getSelectSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT obj FROM ");
		sb.append(this.persistentClass.getSimpleName());
		sb.append(" obj");
		return sb.toString();
	}


}
