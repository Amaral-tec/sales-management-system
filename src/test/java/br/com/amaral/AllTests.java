package br.com.amaral;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.amaral.integration_test.CustomerDAOIntegrationTest;
import br.com.amaral.integration_test.ProductDAOIntegrationTest;
import br.com.amaral.integration_test.SaleDAOIntegrationTest;
import br.com.amaral.unit_test.CustomerDAOTest;
import br.com.amaral.unit_test.CustomerServiceTest;
import br.com.amaral.unit_test.ProductDAOTest;
import br.com.amaral.unit_test.ProductServiceTest;

/**
 * @author leandro.amaral
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ CustomerDAOTest.class, CustomerServiceTest.class, ProductDAOTest.class, CustomerServiceTest.class,
		ProductServiceTest.class, SaleDAOIntegrationTest.class, CustomerDAOIntegrationTest.class,
		ProductDAOIntegrationTest.class })
public class AllTests {

}
