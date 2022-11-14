package br.com.amaral.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author leandro.amaral
 *
 */
@Entity
@Table(name = "TB_SALES")
public class Sale implements Persistent {

	public enum Status {
		STARTED, COMPLETED, CANCELLED;

		public static Status getByName(String value) {
			for (Status status : Status.values()) {
				if (status.name().equals(value)) {
					return status;
				}
			}
			return null;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_seq")
	@SequenceGenerator(name = "sale_seq", sequenceName = "sq_sale", initialValue = 1, allocationSize = 1)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;

	@Column(name = "CODE", nullable = false, unique = true)
	private Long code;

	@ManyToOne
	@JoinColumn(name = "id_customer_fk", foreignKey = @ForeignKey(name = "fk_sale_customer"), referencedColumnName = "id", nullable = false)
	private Customer customer;

	/*
	 * OBS: Não é uma boa prática utiliar FetchType.EAGER pois ele sempre irá trazer
	 * todos os objetos da collection mesmo sem precisar utilizar. Fazer um método
	 * específico para buscar tudo e utilizar quando precisar
	 * 
	 * @see IVendaDAO searchWithCollection
	 */
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL/* , fetch = FetchType.EAGER */)
	private Set<QuantityOfProducts> products;

	@Column(name = "AMOUNT", nullable = false)
	private BigDecimal amount;

	@Column(name = "DATA_SALE", nullable = false)
	private Instant dateSale;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_SALE", nullable = false)
	private Status status;

	public Sale() {
		products = new HashSet<>();
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<QuantityOfProducts> getProducts() {
		return products;
	}

	public void addProduct(Product product, Integer quantity) {
		validateStatus();
		Optional<QuantityOfProducts> op = products.stream()
				.filter(filter -> filter.getProduct().getCode().equals(product.getCode())).findAny();
		if (op.isPresent()) {
			QuantityOfProducts productQt = op.get();
			productQt.add(quantity);
		} else {
			// Criar fabrica para criar ProductQuantity
			QuantityOfProducts prod = new QuantityOfProducts();
			prod.setProduct(product);
			prod.add(quantity);
			products.add(prod);
		}
		recalculateAmountSale();
	}

	private void validateStatus() {
		if (this.status == Status.COMPLETED) {
			throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
		}
	}

	public void removeProduct(Product product, Integer quantity) {
		validateStatus();
		Optional<QuantityOfProducts> op = products.stream()
				.filter(filter -> filter.getProduct().getCode().equals(product.getCode())).findAny();

		if (op.isPresent()) {
			QuantityOfProducts productQt = op.get();
			if (productQt.getQuantity() > quantity) {
				productQt.remove(quantity);
				recalculateAmountSale();
			} else {
				products.remove(op.get());
				recalculateAmountSale();
			}
		}
	}

	public void removeAllProducts() {
		validateStatus();
		products.clear();
		amount = BigDecimal.ZERO;
	}

	public Integer getQuantityAllProducts() {
		// Soma a quantidade getQuantidade() de todos os objetos ProdutoQuantidade
		int result = products.stream().reduce(0, (partialCountResult, prod) -> partialCountResult + prod.getQuantity(),
				Integer::sum);
		return result;
	}

	public void recalculateAmountSale() {
		// validarStatus();
		BigDecimal amount = BigDecimal.ZERO;
		for (QuantityOfProducts prod : this.products) {
			amount = amount.add(prod.getAmount());
		}
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Instant getDateSale() {
		return dateSale;
	}

	public void setDateSale(Instant dateSale) {
		this.dateSale = dateSale;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setProducts(Set<QuantityOfProducts> products) {
		this.products = products;
	}

}
