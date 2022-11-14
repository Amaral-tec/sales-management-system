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
import br.com.amaral.services.IProductService;
import br.com.amaral.services.ProductService;

/**
 * @author leandro.amaral
 *
 */
public class ProductServiceTest {
	
	private IProductService productService;

	private Product product;

	public ProductServiceTest() {
		IProductDAO dao = new ProductDaoMock();
		productService = new ProductService(dao);
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
	public void saveProduct() throws KeyTypeNotFoundException, DAOException {
		Product result = productService.save(product);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void searchProduct() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Product productConsulted = productService.search(product.getId());
		Assert.assertNotNull(productConsulted);
	}
	
	@Test
	public void deleteProduct() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		productService.delete(product);
	}
		
	@Test
	public void updateProduct() throws KeyTypeNotFoundException, DAOException {
		product.setName("Product Test");
		productService.update(product);
		
		Assert.assertEquals("Product Test", product.getName());
	}
	
	
}
