package com.personalproject.universal_pet_care.utils;

import lombok.Getter;

@Getter
public class FeedbackMessage {
    public static final String CREATE_SUCCESS = "Created successfully";
    public static final String UPDATE_SUCCESS = "Updated successfully";
    public static final String DELETE_SUCCESS = "Deleted successfully";
    public static final String GET_SUCCESS = "Get successfully";
    public static final String AUTHENTICATE_SUCCESS = "Authenticated successfully";

    private FeedbackMessage() {}
}
