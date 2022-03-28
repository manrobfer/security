package br.com.javajwt.constant;

public class SecurityConstant {
	
	public static final int EXPERATION_TIME = 432_000_000;
	public static final String TOEKEN_PREFIX = "Bearer ";
	public static final String JWT_TOEKN_HEADER = "Jwt-Token";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
	public static final String GET_ARRAYS_LLC = "Get arrays, LLC";
	public static final String GET_ARRAYS_ADMINISTRATION = "User management portal";
	public static final String AUTHORIIES = "authorities";
	public static final String FORBIDEN_MESSAGE = "You need to login to acces this page";
	public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to acces this page";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	public static final String[] PUBLIC_URLS = {"/user/login", "/user/register/", "/user/resetpassword/**", "/user/image/**" };
	

}
