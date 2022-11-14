package br.com.amaral.services;

import br.com.amaral.dao.IProductDAO;
import br.com.amaral.domain.Product;
import br.com.amaral.services.generics.GenericService;

/**
 * @author leandro.amaral
 *
 */
public class ProductService extends GenericService<Product, Long> implements IProductService {

	public ProductService(IProductDAO dao) {
		super(dao);
	}
}

