package com.mallapp.Constants;

public class ApiConstants {

	public static final String URL_KEY = "http://52.28.59.218:5001/api/";

	public static final String ACCOUNT_URL_KEY = URL_KEY+"Account/";

	public static final String MALL_SERVICE_URL_KEY = URL_KEY+"MallService/";

	public static final String USER_INTEREST_SERVICE_URL_KEY = URL_KEY+"UserInterestService/";

	public static final String SHOPS_SERVICE_URL_KEY = URL_KEY+"ShopService/";

	public static final String ACTIVITY_SERVICE_URL_KEY = URL_KEY+"ActivityService/";

	public static final String FAVOURITE_SERVICE_URL_KEY = URL_KEY+"UserFavoritesService/";

	public static final String RESTAURANT_SERVICE_URL_KEY = URL_KEY+"RestaurantService/";


	public static final String SEND_SMS_URL_KEY =  ACCOUNT_URL_KEY+"Register/";

	public static final String VERIFY_SMS_URL_KEY = ACCOUNT_URL_KEY+"VerifyCode";

	public static final String FB_REG_URL_KEY = ACCOUNT_URL_KEY+"RegisterUser/";

	public static final String PROFILE_UPDATE_URL_KEY = ACCOUNT_URL_KEY+"UpdateProfile";

	public static final String PROFILE_GET_URL_KEY = ACCOUNT_URL_KEY+"GetProfile/";

	public static final String GET_MALL_URL_KEY = MALL_SERVICE_URL_KEY +"/GetMalls?countryCode=DK&languageId=1";

	public static final String POST_FAV_MALL_URL_KEY = MALL_SERVICE_URL_KEY +"SaveSubscribedMalls";

	public static final String GET_USER_MALL_URL_KEY = MALL_SERVICE_URL_KEY +"GetSubscribedMalls/";

	public static final String GET_NEWS_OFFERS_URL_KEY = ACTIVITY_SERVICE_URL_KEY +"GetMallActivities?UserId=";

	public static final String POST_SELECTED_INTEREST_URL_KEY = USER_INTEREST_SERVICE_URL_KEY +"SaveUserInterests";

	public static final String GET_INTEREST_URL_KEY = USER_INTEREST_SERVICE_URL_KEY +"GetUserInterest?languageId=1";

	public static final String GET_SELECTED_INTEREST_URL_KEY = USER_INTEREST_SERVICE_URL_KEY +"GetSelectedInterest?UserId=";

	public static final String GET_SHOPS_URL_KEY = SHOPS_SERVICE_URL_KEY +"GetShops/";

	public static final String GET_SHOP_DETAIL_URL_KEY = SHOPS_SERVICE_URL_KEY +"GetShopDetail?MallStoreId=";

	public static final String POST_FAV_OFFERS_URL_KEY = FAVOURITE_SERVICE_URL_KEY +"SaveFavoriteOffers?UserId=";

	public static final String POST_FAV_SHOP_URL_KEY = FAVOURITE_SERVICE_URL_KEY +"SaveFavoriteShops?UserId=";

	public static final String POST_FAV_RESTAURANT_URL_KEY = FAVOURITE_SERVICE_URL_KEY +"SaveFavoriteRestaurants?UserId=";
	//http://52.28.59.218:5001/api/UserFavoritesService/SaveFavoriteShops?UserId=%7bUserId%7d&EntityId=%7bEntityId%7d&d={EntityId}&IsShop=true?IsDeleted={IsDeleted}
	public static final String GET_RESTAURANT_URL_KEY = RESTAURANT_SERVICE_URL_KEY +"GetRestaurants/";
}
