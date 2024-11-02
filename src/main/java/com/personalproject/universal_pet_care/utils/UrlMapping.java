package com.personalproject.universal_pet_care.utils;

import lombok.Getter;

@Getter
public class UrlMapping {
    public static final String API = "/api/v1";

//    User API
    public static final String USERS = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/user/update/{id}";
    public static final String GET_USER_BY_ID = "/user/{id}";
    public static final String DELETE_USER_BY_ID = "/user/delete/{id}";
    public static final String GET_ALL_USERS = "/all-users";

//    Appointment API
    public static final String APPOINTMENTS = API + "/appointments";
    public static final String BOOK_APPOINTMENT = "/book-appointment";

    private UrlMapping() {}
}
