package br.com.amaral.dao;

import br.com.amaral.dao.generics.GenericDAO;
import br.com.amaral.domain.Customer;

/**
 * @author leandro.amaral
 *
 */
public class CustomerDAO extends GenericDAO<Customer, Long> implements ICustomerDAO {


	public CustomerDAO() {
		super(Customer.class);
	}


}
