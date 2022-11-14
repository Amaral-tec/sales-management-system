package br.com.amaral.dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.amaral.domain.Customer;
import br.com.amaral.domain.Sale;
import br.com.amaral.domain.Sale.Status;

/**
 * @author leandro.amaral
 *
 */
public class SaleFactory {
	
	public static Sale convert(ResultSet rs) throws SQLException {
		Customer customer = CustomerFactory.convert(rs);
		Sale sale = new Sale();
		sale.setCustomer(customer);
		sale.setId(rs.getLong("ID_SALE"));
		sale.setCode(rs.getLong("CODE"));
		sale.setAmount(rs.getBigDecimal("AMOUNT"));
		sale.setDateSale(rs.getTimestamp("DATE_SALE").toInstant());
		sale.setStatus(Status.getByName(rs.getString("STATUS_SALE")));
		return sale;
	}

}