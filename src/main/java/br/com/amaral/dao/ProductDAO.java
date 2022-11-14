package br.com.amaral.dao;

import br.com.amaral.dao.generics.GenericDAO;
import br.com.amaral.domain.Product;

/**
 * @author leandro.amaral
 *
 */
public class ProductDAO extends GenericDAO<Product, Long> implements IProductDAO  {
	
	public ProductDAO() {
		super(Product.class);
	}
}
