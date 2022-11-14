package br.com.amaral.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author leandro.amaral
 *
 */

@Entity
@Table(name = "TB_PRODUCTS")
public class Product implements Persistent {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="prod_seq")
	@SequenceGenerator(name="prod_seq", sequenceName="sq_product", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "CODE", nullable = false, unique = true)
	private Long code;
	
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;
	
	@Column(name = "DESCRIPTION", nullable = false, length = 50)
	private String description;
	
	@Column(name = "VALUE", nullable = false)
	private BigDecimal value;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
