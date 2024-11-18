package com.personalproject.universal_pet_care.utils;

import lombok.Getter;

@Getter
public class UrlMapping {
    public static final String API = "/api/v1";

//    User API
    public static final String USERS = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/user/{id}";
    public static final String GET_USER_BY_ID = "/user/{id}";
    public static final String DELETE_USER_BY_ID = "/user/{id}";
    public static final String GET_ALL_USERS = "/all-users";
    public static final String GET_USER_DETAILS = "/user/details/{id}";

//    Appointment API
    public static final String APPOINTMENTS = API + "/appointments";
    public static final String BOOK_APPOINTMENT = "/book-appointment";
    public static final String GET_ALL_APPOINTMENTS = "/all-appointments";
    public static final String UPDATE_APPOINTMENT = "/appointment/{id}";
    public static final String DELETE_APPOINTMENT = "/appointment/{id}";
    public static final String GET_APPOINTMENT_BY_ID = "/appointment/{id}";
    public static final String GET_APPOINTMENT_BY_NO = "/appointment/no/{no}";

//    Pet APT
    public static final String PETS = API + "/pets";
    public static final String GET_ALL_PETS = "/all-pets";
    public static final String UPDATE_PET = "/pet/{id}";
    public static final String DELETE_PET = "/pet/{id}";
    public static final String GET_PET_BY_ID = "/pet/{id}";

//    Photo API
    public static final String PHOTOS = API + "/photos";
    public static final String UPLOAD_PHOTO = "/upload-photo";
    public static final String UPDATE_PHOTO = "/photo/{id}";
    public static final String DELETE_PHOTO = "/photo/{photoId}/user/{userId}";
    public static final String GET_PHOTO_BY_ID = "/photo/{id}";

//    Review API
    public static final String REVIEWS = API + "/reviews";
    public static final String SUBMIT_REVIEW = "/submit-review/{reviewerId}/{veterinarianId}";
    public static final String UPDATE_REVIEW = "/review/{id}";
    public static final String DELETE_REVIEW = "review/{id}";
    public static final String GET_AVERAGE_STARS_FOR_VET = "/vet/avg-star/{veterinarianId}";
    public static final String GET_REVIEWS_BY_ID = "/user/{id}";

//    constructor
    private UrlMapping() {}
}
