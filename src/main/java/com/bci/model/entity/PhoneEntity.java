package com.bci.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String citycode;
    private String countrycode;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    public PhoneEntity() {
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return citycode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String citycode) {
		this.citycode = citycode;
	}

	/**
	 * @return the countrycode
	 */
	public String getCountryCode() {
		return countrycode;
	}

	/**
	 * @param countrycode the countrycode to set
	 */
	public void setCountryCode(String countrycode) {
		this.countrycode = countrycode;
	}

	/**
	 * @return the user
	 */
	public UserEntity getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}

    
}
