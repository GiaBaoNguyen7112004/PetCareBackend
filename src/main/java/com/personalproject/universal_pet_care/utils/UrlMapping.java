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
    public static final String GET_ALL_APPOINTMENTS = "/all-appointments";
    public static final String UPDATE_APPOINTMENT = "/appointment/update/{id}";
    public static final String DELETE_APPOINTMENT = "/appointment/delete/{id}";
    public static final String GET_APPOINTMENT_BY_ID = "/appointment/{id}";
    public static final String GET_APPOINTMENT_BY_NO = "/appointment/no/{no}";

//    constructor
    private UrlMapping() {}
}
