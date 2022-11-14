package br.com.amaral.domain;

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
@Table(name = "TB_CUSTOMERS")
public class Customer implements Persistent {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
	@SequenceGenerator(name = "customer_seq", sequenceName = "sq_customer", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	@Column(name = "CPF", nullable = false, unique = true)
	private Long cpf;

	@Column(name = "MOBILEPHONE", nullable = false)
	private Long mobilePhone;

	@Column(name = "EMAIL", nullable = false, length = 50)
	private String email;

	@Column(name = "ADDRESS", nullable = false, length = 50)
	private String address;

	@Column(name = "NUMBER", nullable = false)
	private Integer number;

	@Column(name = "DISTRICT", nullable = false, length = 50)
	private String district;

	@Column(name = "CITY", nullable = false, length = 50)
	private String city;

	@Column(name = "STATE", nullable = false, length = 50)
	private String state;

	@Column(name = "CEP", nullable = false)
	private Long cep;

	@Column(name = "PASSWORD", nullable = false, length = 15)
	private String password;

	@Column(name = "PHOTO", nullable = false, length = 100)
	private String photo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Long getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(Long mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Customer{" + "name='" + name + '\'' + ", cpf=" + cpf + ", mobilePhone=" + mobilePhone + ", email='"
				+ email + '\'' + '}';
	}

}

