package br.com.amaral.dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.amaral.domain.Customer;

/**
 * @author leandro.amaral
 *
 */
public class CustomerFactory {
	
	public static Customer convert(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong("ID_CUSTOMER"));
		customer.setName(rs.getString(("NAME")));
		customer.setCpf(rs.getLong(("CPF")));
		customer.setMobilePhone(rs.getLong(("MOBILEPHONE")));
		customer.setEmail(rs.getString(("EMAIL")));
		customer.setAddress(rs.getString(("ADDRESS")));
		customer.setNumber(rs.getInt(("NUMBER")));
		customer.setDistrict(rs.getString(("DISTRICT")));
		customer.setState(rs.getString(("STATE")));
		customer.setCep(rs.getLong(("CEP")));
		return customer;
	}

}