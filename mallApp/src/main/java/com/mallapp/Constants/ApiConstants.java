package com.mallapp.Constants;

public class ApiConstants {

	public static final String URL_KEY = "http://52.28.59.218:5001/api/";

	public static final String ACCOUNT_URL_KEY = URL_KEY+"Account/";

	public static final String MALL_SERVICE_URL_KEY = URL_KEY+"MallService/";

	public static final String USER_INTEREST_SERVICE_URL_KEY = URL_KEY+"UserInterestService/";

	public static final String ACTIVITY_SERVICE_URL_KEY = URL_KEY+"ActivityService/";

	public static final String SEND_SMS_URL_KEY =  ACCOUNT_URL_KEY+"Register/";

	public static final String VERIFY_SMS_URL_KEY = ACCOUNT_URL_KEY+"VerifyCode";

	public static final String FB_REG_URL_KEY = ACCOUNT_URL_KEY+"RegisterUser/";

	public static final String PROFILE_UPDATE_URL_KEY = ACCOUNT_URL_KEY+"UpdateProfile";

	public static final String PROFILE_GET_URL_KEY = ACCOUNT_URL_KEY+"GetProfile/";

	public static final String GET_MALL_URL_KEY = MALL_SERVICE_URL_KEY +"/GetMalls?countryCode=PK&languageId=1";

	public static final String POST_FAV_MALL_URL_KEY = MALL_SERVICE_URL_KEY +"SaveSubscribedMalls";

	public static final String GET_USER_MALL_URL_KEY = MALL_SERVICE_URL_KEY +"GetSubscribedMalls/";

	public static final String GET_NEWS_OFFERS_URL_KEY = ACTIVITY_SERVICE_URL_KEY +"GetMallActivities?UserId=";

	public static final String POST_SELECTED_INTEREST_URL_KEY = USER_INTEREST_SERVICE_URL_KEY +"SaveUserInterests";

	public static final String GET_INTEREST_URL_KEY = USER_INTEREST_SERVICE_URL_KEY +"GetUserInterest?languageId=1";

	public static final String GET_SELECTED_INTEREST_URL_KEY = USER_INTEREST_SERVICE_URL_KEY +"GetSelectedInterest?UserId=";
}
