package com.mallapp.Model;

import java.io.Serializable;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class ContryInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -752796197742164028L;


    private String countryiso;
    private String countryName;
    private String dialCode;


    public ContryInfo() {
        // TODO Auto-generated constructor stub
    }


    /**
     * @return the countryiso
     */
    public String getCountryiso() {
        return countryiso;
    }


    /**
     * @param countryiso the countryiso to set
     */
    public void setCountryiso(String countryiso) {
        this.countryiso = countryiso;
    }


    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }


    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    /**
     * @return the dialCode
     */
    public String getDialCode() {
        return dialCode;
    }


    /**
     * @param dialCode the dialCode to set
     */
    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }




}

