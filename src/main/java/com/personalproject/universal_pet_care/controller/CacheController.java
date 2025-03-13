package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.cache.CacheService;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.CACHE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CacheController {
    CacheService cacheService;

    @PostMapping(UrlMapping.CLEAR_CACHE)
    public ResponseEntity<ApiResponse> clearCache() {
        cacheService.clearCache();
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.CLEAR_CACHE_SUCCESS)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_TIME_TO_LIVE)
    public ResponseEntity<ApiResponse> getTimeToLive(@PathVariable String key) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.GET_TIME_TO_LIVE_SUCCESS)
                .data(cacheService.getTimeToLive(key))
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
