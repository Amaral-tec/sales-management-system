package br.com.amaral.unit_test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.amaral.dao.IProductDAO;
import br.com.amaral.dao.ProductDaoMock;
import br.com.amaral.domain.Product;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;
import br.com.amaral.exceptions.MoreThanOneRecordException;
import br.com.amaral.exceptions.TableException;

/**
 * @author leandro.amaral
 *
 */
public class ProductDAOTest {
	
	private IProductDAO productDao;

	private Product product;
	
	public ProductDAOTest() {
		productDao = new ProductDaoMock();
	}
	
	@Before
	public void init() {
		product = new Product();
		product.setCode(1L);
		product.setDescription("Product 1");
		product.setName("Product 1");
		product.setValue(BigDecimal.TEN);
	}	
	
	@Test
	public void saveProduct() throws KeyTypeNotFoundException,  MoreThanOneRecordException, TableException, DAOException {
		Product result = productDao.save(product);
		Assert.assertNotNull(result);
	}
		
	@Test
	public void searchProduct() throws MoreThanOneRecordException, TableException, DAOException {
		Product consultedProduct = productDao.search(product.getId());
		Assert.assertNotNull(consultedProduct);
	}
	
	@Test
	public void deleteProduct() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Product result = productDao.save(product);
		Assert.assertNotNull(result);
		
		Product consultedProduct = productDao.search(product.getId());
		Assert.assertNotNull(consultedProduct);

		productDao.delete(product);

		Product consultedProduct1 = productDao.search(product.getId());
		Assert.assertNull(consultedProduct1);
		
	}
		
	@Test
	public void updateProduct() throws KeyTypeNotFoundException, DAOException {
		product.setName("Product Test");
		productDao.update(product);
		
		Assert.assertEquals("Product Test", product.getName());
	}
	
}

