package com.duyhai.bookweb_backend.security;

public class Endpoints  {
    public static final String front_end_host = "http://localhost:3000";

    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/books",
            "/books/**",
            "/ratings",
            "/ratings/**",
            "/images/",
            "/images/**",
            "/account/register",
            "/users/search/existsByUsername",
            "/users/search/existsByEmail",
            "/account/active",
            "/api/delivery-methods",
            "/api/delivery-methods/**",
            "/api/favourite-books",
            "/api/favourite-books/**",
            "favourite-books/**",
    };
    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/account/register",
            "/account/create-idOfActivation/**",
            "/account/create-idOfActivation",
            "/account/login",
            "/images/",
            "/images/**",
            "/api/delivery-methods",
            "/api/delivery-methods/**",
            "/api/favourite-books",
            "/api/favourite-books/**",
            "/api/favourite-books/add/**",
            "favourite-books/**",
    };
    public static final String[] PUBLIC_DELETE_ENDPOINTS = {
        "/api/favourite-books/remove-favourite/**",
        "/api/favourite-books/**",
    };
    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/users",
            "/users/**",
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/books",
            "/api/books",

    };
    public static final String[] ADMIN_DELETE_ENDPOINTS = {
            "/books",
            "/api/books",
            "/api/books/**",
            "/api/images/**",

    };

    public static final String[] ADMIN_PUT_ENDPOINTS = {
            "/api/books/**",

    };


}
