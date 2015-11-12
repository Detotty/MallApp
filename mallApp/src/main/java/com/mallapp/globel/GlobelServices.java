package com.mallapp.globel;

public class GlobelServices {

	public static final String URL_KEY = "http://52.28.59.218:5001/api/";
	public static final String ACCOUNT_URL_KEY = "http://52.28.59.218:5001/api/Account/";
	public static final String SEND_SMS_URL_KEY =  "http://52.28.59.218:5001/api/Account/Register/";
//	public static final String SEND_SMS_URL_KEY =  "https://crowdeyews-test.azurewebsites.net/WCFService.svc/SendValidationCode";
	public static final String VERIFY_SMS_URL_KEY ="http://52.28.59.218:5001/api/Account/VerifyCode";
	public static final String FB_REG_URL_KEY ="http://52.28.59.218:5001/api/Account/RegisterUser/";
	public static final String PROFILE_UPDATE_URL_KEY = "http://52.28.59.218:5001/api/Account/UpdateProfile";
	public static final String GET_MALL_URL_KEY = "http://52.28.59.218:5001/api/MallService/GetMalls?countryCode=PK&languageId=1";
	public static final String POST_FAV_MALL_URL_KEY = "http://52.28.59.218:5001/api/MallService/SaveSubscribedMalls";
	public static final String POST_SELECTED_INTEREST_URL_KEY = "http://52.28.59.218:5001/api/UserInterestService/SaveUserInterests";
	public static final String GET_INTEREST_URL_KEY = "http://52.28.59.218:5001/api/UserInterestService/GetUserInterest?languageId=1";
//	public static final String VERIFY_SMS_URL_KEY ="https://crowdeyews-test.azurewebsites.net/WCFService.svc/ValidatePhoneNumber";
}
