package br.com.amaral.dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.amaral.domain.Product;
import br.com.amaral.domain.QuantityOfProducts;

/**
 * @author leandro.amaral
 *
 */
public class ProductQuantityFactory {
	
	public static QuantityOfProducts convert(ResultSet rs) throws SQLException {
		Product prod = ProductFactory.convert(rs);
		QuantityOfProducts prodQ = new QuantityOfProducts();
		prodQ.setProduct(prod);
		prodQ.setId(rs.getLong("ID"));
		prodQ.setQuantity(rs.getInt("QUANTITY"));
		prodQ.setAmount(rs.getBigDecimal("AMOUNT"));
		return prodQ;
	}

}
