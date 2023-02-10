package br.com.amaral.integration_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.amaral.dao.CustomerDAO;
import br.com.amaral.dao.ICustomerDAO;
import br.com.amaral.dao.IProductDAO;
import br.com.amaral.dao.ISaleDAO;
import br.com.amaral.dao.ProductDAO;
import br.com.amaral.dao.SaleDAO;
import br.com.amaral.dao.generics.jdbc.ConnectionFactoryTest;
import br.com.amaral.domain.Customer;
import br.com.amaral.domain.Product;
import br.com.amaral.domain.Sale;
import br.com.amaral.domain.Sale.Status;
import br.com.amaral.exceptions.DAOException;
import br.com.amaral.exceptions.KeyTypeNotFoundException;
import br.com.amaral.exceptions.MoreThanOneRecordException;
import br.com.amaral.exceptions.TableException;

/**
 * @author leandro.amaral
 *
 */
public class SaleDAOIntegrationTest {

	private ISaleDAO saleDao;

	private ICustomerDAO customerDao;

	private IProductDAO productDao;

	private Customer customer;

	private Product product;

	public SaleDAOIntegrationTest() {
		saleDao = new SaleDAO();
		customerDao = new CustomerDAO();
		productDao = new ProductDAO();
	}

	@Before
	public void init() throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		this.customer = saveCustomer();
		this.product = saveProduct(1L, BigDecimal.TEN);
	}

	@After
	public void end() throws DAOException {
		deleteSale();
		deleteProduct();
		customerDao.delete(this.customer);
	}

	private void deleteProduct() throws DAOException {
		Collection<Product> list = this.productDao.searchAll();
		for (Product prod : list) {
			this.productDao.delete(prod);
		}
	}

	@Test
	public void search()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Sale sale = createSale(1L);
		Sale consultedSale = saleDao.search(sale.getCode());
		assertNotNull(consultedSale);
		assertEquals(sale.getCode(), consultedSale.getCode());
	}

	@Test
	public void save()
			throws KeyTypeNotFoundException, DAOException, MoreThanOneRecordException, TableException {
		Sale sale = createSale(1L);

		assertTrue(sale.getAmount().equals(BigDecimal.valueOf(20)));
		assertTrue(sale.getStatus().equals(Status.STARTED));

		Sale consultedSale = saleDao.search(sale.getCode());
		assertTrue(consultedSale.getId() != null);
		assertEquals(sale.getCode(), consultedSale.getCode());
	}

	@Test
	public void cancelSale()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Long codeSale = 1L;
		Sale sale = createSale(codeSale);
		assertNotNull(sale);
		assertEquals(codeSale, sale.getCode());

		saleDao.cancelSale(sale);

		Sale consultedSale = saleDao.search(codeSale);
		assertEquals(codeSale, consultedSale.getCode());
		assertEquals(Status.CANCELLED, consultedSale.getStatus());
	}

	@Test
	public void adicionarMaisProdutosDoMesmo()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Long codeSale = 4L;
		Sale sale = createSale(codeSale);
		assertNotNull(sale);
		assertEquals(codeSale, sale.getCode());

		Sale consultedSale = saleDao.search(codeSale);
		consultedSale.addProduct(product, 1);

		assertTrue(consultedSale.getQuantityAllProducts() == 3);
		BigDecimal amount = BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(consultedSale.getAmount().equals(amount));
		assertTrue(consultedSale.getStatus().equals(Status.STARTED));
	}

	@Test
	public void adicionarMaisProdutosDiferentes()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Long codeSale = 5L;
		Sale sale = createSale(codeSale);
		assertNotNull(sale);
		assertEquals(codeSale, sale.getCode());

		Product prod = saveProduct(codeSale, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codeSale, prod.getCode());

		Sale consultedSale = saleDao.search(codeSale);
		consultedSale.addProduct(prod, 1);

		assertTrue(consultedSale.getQuantityAllProducts() == 3);
		BigDecimal amount = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(consultedSale.getAmount().equals(amount));
		assertTrue(consultedSale.getStatus().equals(Status.STARTED));
	}

	@Test(expected = DAOException.class)
	public void salvarVendaMesmoCodigoExistente() throws KeyTypeNotFoundException, DAOException {
		Sale sale = createSale(6L);
		
		assertTrue(sale.getStatus().equals(Status.STARTED));
	}

	@Test
	public void removerProduto()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Long codeSale = 7L;
		Sale sale = createSale(codeSale);
		assertNotNull(sale);
		assertEquals(codeSale, sale.getCode());

		Product prod = saveProduct(codeSale, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codeSale, prod.getCode());

		Sale consultedSale = saleDao.search(codeSale);
		consultedSale.addProduct(prod, 1);
		assertTrue(consultedSale.getQuantityAllProducts() == 3);
		BigDecimal amount = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(consultedSale.getAmount().equals(amount));

		consultedSale.removeProduct(prod, 1);
		assertTrue(consultedSale.getQuantityAllProducts() == 2);
		amount = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(consultedSale.getAmount().equals(amount));
		assertTrue(consultedSale.getStatus().equals(Status.STARTED));
	}

	@Test
	public void removerApenasUmProduto()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Long codeSale = 8L;
		Sale sale = createSale(codeSale);
		assertNotNull(sale);
		assertEquals(codeSale, sale.getCode());

		Product prod = saveProduct(codeSale, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codeSale, prod.getCode());

		Sale consultedSale = saleDao.search(codeSale);
		consultedSale.addProduct(prod, 1);
		assertTrue(consultedSale.getQuantityAllProducts() == 3);
		BigDecimal amount = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(consultedSale.getAmount().equals(amount));

		consultedSale.removeProduct(prod, 1);
		assertTrue(consultedSale.getQuantityAllProducts() == 2);
		amount = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(consultedSale.getAmount().equals(amount));
		assertTrue(consultedSale.getStatus().equals(Status.STARTED));
	}

	@Test
	public void removeAllProducts()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Long codeSale = 9L;
		Sale sale = createSale(codeSale);
		assertNotNull(sale);
		assertEquals(codeSale, sale.getCode());

		Product prod = saveProduct(codeSale, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codeSale, prod.getCode());

		Sale consultedSale = saleDao.search(codeSale);
		consultedSale.addProduct(prod, 1);
		assertTrue(consultedSale.getQuantityAllProducts() == 3);
		BigDecimal amount = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(consultedSale.getAmount().equals(amount));

		consultedSale.removeAllProducts();
		assertTrue(consultedSale.getQuantityAllProducts() == 0);
		assertTrue(consultedSale.getAmount().equals(BigDecimal.valueOf(0)));
		assertTrue(consultedSale.getStatus().equals(Status.STARTED));
	}

	@Test
	public void finalizarVenda()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Long codeSale = 1L;
		Sale sale = createSale(codeSale);
		assertNotNull(sale);
		assertEquals(codeSale, sale.getCode());

		saleDao.finalizeSale(sale);

		Sale consultedSale = saleDao.search(codeSale);
		assertEquals(sale.getCode(), consultedSale.getCode());
		assertEquals(Status.COMPLETED, consultedSale.getStatus());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void tentarAdicionarProdutosVendaFinalizada()
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Long codeSale = 11L;
		Sale sale = createSale(codeSale);
		assertNotNull(sale);
		assertEquals(codeSale, sale.getCode());

		saleDao.finalizeSale(sale);
		Sale consultedSale = saleDao.search(codeSale);
		assertEquals(sale.getCode(), consultedSale.getCode());
		assertEquals(Status.COMPLETED, consultedSale.getStatus());

		consultedSale.addProduct(this.product, 1);

	}

	private Product saveProduct(Long code, BigDecimal value)
			throws KeyTypeNotFoundException, MoreThanOneRecordException, TableException, DAOException {
		Product product = new Product();
		product.setCode(code);
		product.setDescription("Produto 1");
		product.setName("Produto 1");
		product.setValue(value);
		productDao.save(product);
		return product;
	}

	private Customer saveCustomer() throws KeyTypeNotFoundException, DAOException {
		Customer customer = new Customer();
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
		return customer;
	}

	private Sale createSale(Long code) {
		Sale sale = new Sale();
		sale.setCode(code);
		sale.setDateSale(Instant.now());
		sale.setCustomer(this.customer);
		sale.setStatus(Status.STARTED);
		sale.addProduct(this.product, 2);
		return sale;
	}

	private void deleteSale() throws DAOException {
		String sqlProd = "DELETE FROM TB_PRODUCT_QUANTITY";
		executeDelete(sqlProd);

		String sqlV = "DELETE FROM TB_SALES";
		executeDelete(sqlV);
	}

	private void executeDelete(String sql) throws DAOException {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			stm = connection.prepareStatement(sql);
			stm.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("ERRO EXLUINDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, rs);
		}
	}

	protected void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (stm != null && !stm.isClosed()) {
				stm.close();
			}
			if (connection != null && !stm.isClosed()) {
				connection.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	protected Connection getConnection() throws DAOException {
		try {
			return ConnectionFactoryTest.getConnection();
		} catch (SQLException e) {
			throw new DAOException("ERRO ABRINDO CONEXAO COM BANCO DE DADOS ", e);
		}
	}

}
