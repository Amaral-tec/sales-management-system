package br.com.amaral.services;

import br.com.amaral.domain.Customer;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.services.generics.IGenericService;

/**
 * @author leandro.amaral
 *
 */
public interface ICustomerService extends IGenericService<Customer, Long> {


	Customer searchForCpf(Long cpf) throws DAOException;

}
