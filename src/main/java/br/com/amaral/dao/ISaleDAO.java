package br.com.amaral.dao;

import br.com.amaral.dao.generics.IGenericDAO;
import br.com.amaral.domain.Sale;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;

/**
 * @author leandro.amaral
 *
 */
public interface ISaleDAO extends IGenericDAO<Sale, Long> {

	public void finalizeSale(Sale sale) throws KeyTypeNotFoundException, DAOException;

	public void cancelSale(Sale sale) throws KeyTypeNotFoundException, DAOException;
	
	public Sale searchWithCollection(Long id);

}
