package br.com.amaral.dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.amaral.domain.Product;

/**
 * @author leandro.amaral
 *
 */
public class ProductFactory {

	public static Product convert(ResultSet rs) throws SQLException {
		Product prod = new Product();
		prod.setId(rs.getLong("ID_PRODUCT"));
		prod.setCode(rs.getLong("CODE"));
		prod.setName(rs.getString("NAME"));
		prod.setDescription(rs.getString("DESCRIPTION"));
		prod.setValue(rs.getBigDecimal("VALUE"));
		return prod;
	}
}