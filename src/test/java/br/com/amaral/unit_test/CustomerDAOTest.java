package br.com.amaral.unit_test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.amaral.dao.CustomerDaoMock;
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
public class CustomerDAOTest {

	private ICustomerDAO customerDao;

	private Customer customer;

	public CustomerDAOTest() {
		customerDao = new CustomerDaoMock();
	}

	@Before
	public void init() {
		customer = new Customer();
		customer.setName("Customer Test");
		customer.setCpf(12345678912L);
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
	}

	@Test
	public void saveCustomer() throws KeyTypeNotFoundException,  MoreThanOneRecordException, TableException, DAOException {
		Customer result = customerDao.save(customer);
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void searchCustomer() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Customer consultedCustomer = customerDao.search(customer.getId());
		Assert.assertNotNull(consultedCustomer);
	}

	@Test
	public void deleteCustomer() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Customer result = customerDao.save(customer);
		Assert.assertNotNull(result);
		
		Customer consultedCustomer = customerDao.search(customer.getId());
		Assert.assertNotNull(consultedCustomer);

		customerDao.delete(customer);

		Customer consultedCustomer1 = customerDao.search(customer.getId());
		Assert.assertNull(consultedCustomer1);
		
	}
	
	@Test
	public void updateCustomer() throws KeyTypeNotFoundException, DAOException {
		customer.setName("Name Update");
		customer.setCpf(12345678912L);
		customer.setMobilePhone(84999992222L);
		customer.setEmail("update@email.com");
		customer.setAddress("Street Update");
		customer.setNumber(1);
		customer.setDistrict("Update");
		customer.setCity("Update");
		customer.setState("Update");
		customer.setCep(50000000L);
		customer.setPassword("123");
		customer.setPhoto("link");

		customerDao.update(customer);
		Assert.assertEquals("Name Update", customer.getName());
		customerDao.update(customer);
	}
}
