package br.com.amaral.integration_test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.com.amaral.dao.CustomerDAO;
import br.com.amaral.dao.ICustomerDAO;
import br.com.amaral.domain.Customer;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;
import br.com.amaral.exceptions.MoreThanOneRecordException;
import br.com.amaral.exceptions.TableException;

/**
 * @author leandro.amaral
 *
 */
public class CustomerDAOIntegrationTest {

	private ICustomerDAO customerDao;

	private Random rd;

	public CustomerDAOIntegrationTest() {
		this.customerDao = new CustomerDAO();
		rd = new Random();
	}

	private Customer createCustomer() {
		Customer customer = new Customer();
		customer.setName("Customer Test");
		customer.setCpf(rd.nextLong());
		customer.setMobilePhone(84999992222L);
		customer.setEmail("teste@email.com");
		customer.setAddress("Street test");
		customer.setNumber(1);
		customer.setDistrict("District test");
		customer.setCity("City test");
		customer.setState("State test");
		customer.setCep(50000000L);
		customer.setPassword("123");
		customer.setPhoto("link");
		return customer;
	}
	
	@After
	public void end() throws DAOException {
		Collection<Customer> list = customerDao.searchAll();
		list.forEach(cus -> {
			try {
				customerDao.delete(cus);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Test
	public void searchCustomer()
			throws MoreThanOneRecordException, TableException, KeyTypeNotFoundException, DAOException {
		Customer customer = createCustomer();
		customerDao.save(customer);

		Customer consultedCustomer = customerDao.search(customer.getId());
		Assert.assertNotNull(consultedCustomer);

	}
	
	@Test
	public void saveCustomer() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Customer customer = createCustomer();
		Customer result = customerDao.save(customer);
		Assert.assertNotNull(result);

		Customer consultedCustomer = customerDao.search(result.getId());
		Assert.assertNotNull(consultedCustomer);

		customerDao.delete(customer);

		Customer consultedCustomer1 = customerDao.search(result.getId());
		Assert.assertNull(consultedCustomer1);

	}

	@Test
	public void deleteCustomer() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Customer customer = createCustomer();
		Customer result = customerDao.save(customer);
		Assert.assertNotNull(result);

		Customer consultedCustomer = customerDao.search(customer.getId());
		Assert.assertNotNull(consultedCustomer);

		customerDao.delete(customer);

		consultedCustomer = customerDao.search(customer.getId());
		Assert.assertNull(consultedCustomer);

	}

	@Test
	public void updateCustomer()
			throws MoreThanOneRecordException, TableException, KeyTypeNotFoundException, DAOException {
		Customer customer = createCustomer();
		Customer result = customerDao.save(customer);
		Assert.assertNotNull(result);

		Customer consultedCustomer = customerDao.search(customer.getId());
		Assert.assertNotNull(consultedCustomer);

		consultedCustomer.setName("Name Update");
		customerDao.update(consultedCustomer);

		Customer changedCustomer = customerDao.search(consultedCustomer.getId());
		Assert.assertNotNull(changedCustomer);
		Assert.assertEquals("Name Update", changedCustomer.getName());

		customerDao.delete(customer);
		consultedCustomer = customerDao.search(changedCustomer.getId());
		Assert.assertNull(consultedCustomer);
	}

	@Test
	public void searchAll() throws KeyTypeNotFoundException, DAOException {
		Customer customer = createCustomer();
		Customer result = customerDao.save(customer);
		Assert.assertNotNull(result);
		
		Customer customer1 = new Customer();
		Customer result1 = customerDao.save(customer1);
		Assert.assertNotNull(result1);

		Collection<Customer> list = customerDao.searchAll();
		assertTrue(list != null);
		assertTrue(list.size() == 2);

		list.forEach(cus -> {
			try {
				customerDao.delete(cus);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Collection<Customer> list1 = customerDao.searchAll();
		assertTrue(list1 != null);
		assertTrue(list1.size() == 0);
	}

}
