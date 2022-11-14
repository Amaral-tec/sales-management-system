package br.com.amaral.services;

import br.com.amaral.dao.ICustomerDAO;
import br.com.amaral.domain.Customer;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.MoreThanOneRecordException;
import br.com.amaral.exceptions.TableException;
import br.com.amaral.services.generics.GenericService;

/**
 * @author leandro.amaral
 *
 */
public class CustomerService extends GenericService<Customer, Long> implements ICustomerService {
	
	public CustomerService(ICustomerDAO customerDAO) {
		super(customerDAO);
		
	}

	@Override
	public Customer searchForCpf(Long cpf) throws DAOException  {
		try {
			return this.dao.search(cpf);
		} catch (MoreThanOneRecordException | TableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
