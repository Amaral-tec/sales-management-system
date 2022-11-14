package br.com.amaral.dao;

import java.util.Collection;

import br.com.amaral.domain.Customer;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;

/**
 * @author leandro.amaral
 *
 */
public class CustomerDaoMock implements ICustomerDAO {

	@Override
	public Customer save(Customer entity) throws KeyTypeNotFoundException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Customer entity) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Customer update(Customer entity) throws KeyTypeNotFoundException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer search(Long value) {
		Customer customer = new Customer();
		customer.setId(value);
		return customer;
	}

	@Override
	public Collection<Customer> searchAll() {
		// TODO Auto-generated method stub
		return null;
	}

}