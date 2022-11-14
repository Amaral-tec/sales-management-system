package br.com.amaral.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author leandro.amaral
 *
 */
@Table(name = "TB_PRODUCT_QUANTITY")
public class QuantityOfProducts {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="prod_quant_seq")
	@SequenceGenerator(name="prod_quant_seq", sequenceName="sq_product_quant", initialValue = 1, allocationSize = 1)
	private Long id;

	private Product product;
	
	@Column(name = "QUANTITY", nullable = false)
	private Integer quantity;
	
	@Column(name = "AMOUNT", nullable = false)
	private BigDecimal amount;
	
	public QuantityOfProducts() {
		this.quantity = 0;
		this.amount = BigDecimal.ZERO;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void add(Integer quantity) {
		this.quantity += quantity;
		BigDecimal newValue = this.product.getValue().multiply(BigDecimal.valueOf(quantity));
		BigDecimal newAmount = this.amount.add(newValue);
		this.amount = newAmount;
	}
	
	public void remove(Integer quantity) {
		this.quantity -= quantity;
		BigDecimal newValue = this.product.getValue().multiply(BigDecimal.valueOf(quantity));
		this.amount = this.amount.subtract(newValue);
	}
	
}
