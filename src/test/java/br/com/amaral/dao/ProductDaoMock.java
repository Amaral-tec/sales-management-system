package br.com.amaral.dao;

import java.util.Collection;

import br.com.amaral.domain.Product;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;

/**
 * @author leandro.amaral
 *
 */
public class ProductDaoMock implements IProductDAO {

	@Override
	public Product save(Product entity) throws KeyTypeNotFoundException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Product entity) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Product update(Product entity) throws KeyTypeNotFoundException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Product search(Long value) {
		Product product = new Product();
		product.setId(value);
		return product;
	}

	@Override
	public Collection<Product> searchAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
