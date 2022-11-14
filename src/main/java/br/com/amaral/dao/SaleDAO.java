package br.com.amaral.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.amaral.dao.generics.GenericDAO;
import br.com.amaral.domain.Customer;
import br.com.amaral.domain.Product;
import br.com.amaral.domain.Sale;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;

/**
 * @author leandro.amaral
 *
 */
public class SaleDAO extends GenericDAO<Sale, Long> implements ISaleDAO {

	public SaleDAO() {
		super(Sale.class);
	}

	@Override
	public void finalizeSale(Sale sale) throws KeyTypeNotFoundException, DAOException {
		super.update(sale);

	}

	@Override
	public void cancelSale(Sale sale) throws KeyTypeNotFoundException, DAOException {
		super.update(sale);

	}

	@Override
	public void delete(Sale entity) throws DAOException {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");

	}

	@Override
	public Sale save(Sale entity) throws KeyTypeNotFoundException, DAOException {
		try {
			openConnection();
			entity.getProducts().forEach(prod -> {
				Product product = entityManager.merge(prod.getProduct());
				prod.setProduct(product);
			});
			Customer customer = entityManager.merge(entity.getCustomer());
			entity.setCustomer(customer);
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
			closeConnection();
			return entity;
		} catch (Exception e) {
			throw new DAOException("ERRO AO SALVAR VENDA", e);
		}
	}

	@Override
	public Sale searchWithCollection(Long id) {
		openConnection();

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
		Root<Sale> root = query.from(Sale.class);
		root.fetch("customer");
		root.fetch("products");
		query.select(root).where(builder.equal(root.get("id"), id));
		TypedQuery<Sale> tpQuery = entityManager.createQuery(query);
		Sale sale = tpQuery.getSingleResult();
		closeConnection();
		return sale;
	}

}
