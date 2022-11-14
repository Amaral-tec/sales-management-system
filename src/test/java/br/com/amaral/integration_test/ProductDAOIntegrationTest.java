package br.com.amaral.integration_test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.com.amaral.dao.IProductDAO;
import br.com.amaral.dao.ProductDAO;
import br.com.amaral.domain.Product;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;
import br.com.amaral.exceptions.MoreThanOneRecordException;
import br.com.amaral.exceptions.TableException;

/**
 * @author leandro.amaral
 *
 */
public class ProductDAOIntegrationTest {

	private IProductDAO productDao;

	public ProductDAOIntegrationTest() {
			productDao = new ProductDAO();
		}

	@After
	public void end() throws DAOException {
		Collection<Product> list = productDao.searchAll();
		list.forEach(prod -> {
			try {
				productDao.delete(prod);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private Product createProduct() {
		Product product = new Product();
		product.setCode(1L);
		product.setDescription("Product 1");
		product.setName("Product 1");
		product.setValue(BigDecimal.TEN);
		return product;
	}

	@Test
	public void searchProduct()
			throws MoreThanOneRecordException, TableException, KeyTypeNotFoundException, DAOException {
		Product product = createProduct();
		productDao.save(product);
		
		Product consultedProduct = productDao.search(product.getId());
		Assert.assertNotNull(consultedProduct);
	}


	@Test
	public void saveProduct() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Product product = createProduct();
		Product result = productDao.save(product);
		Assert.assertNotNull(result);

		Product consultedProduct = productDao.search(result.getId());
		Assert.assertNotNull(consultedProduct);

		productDao.delete(product);

		Product consultedProduct1 = productDao.search(result.getId());
		Assert.assertNull(consultedProduct1);

	}

	@Test
	public void deleteProduct() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Product product = createProduct();
		Product result = productDao.save(product);
		Assert.assertNotNull(result);

		Product consultedProduct = productDao.search(product.getId());
		Assert.assertNotNull(consultedProduct);

		productDao.delete(product);

		consultedProduct = productDao.search(product.getId());
		Assert.assertNull(consultedProduct);

	}

	@Test
	public void updateProduct()
			throws MoreThanOneRecordException, TableException, KeyTypeNotFoundException, DAOException {
		Product product = createProduct();
		Product result = productDao.save(product);
		Assert.assertNotNull(result);

		Product consultedProduct = productDao.search(product.getId());
		Assert.assertNotNull(consultedProduct);

		consultedProduct.setName("Name Update");
		productDao.update(consultedProduct);

		Product changedProduct = productDao.search(consultedProduct.getId());
		Assert.assertNotNull(changedProduct);
		Assert.assertEquals("Name Update", changedProduct.getName());

		productDao.delete(product);
		consultedProduct = productDao.search(changedProduct.getId());
		Assert.assertNull(consultedProduct);
	}

	@Test
	public void searchAll() throws KeyTypeNotFoundException, DAOException {
		Product product = createProduct();
		Product result = productDao.save(product);
		Assert.assertNotNull(result);
		
		Product product1 = new Product();
		Product result1 = productDao.save(product1);
		Assert.assertNotNull(result1);

		Collection<Product> list = productDao.searchAll();
		assertTrue(list != null);
		assertTrue(list.size() == 2);

		list.forEach(pro -> {
			try {
				productDao.delete(pro);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Collection<Product> list1 = productDao.searchAll();
		assertTrue(list1 != null);
		assertTrue(list1.size() == 0);
	}
	
}
