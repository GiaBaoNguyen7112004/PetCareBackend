package com.personalproject.universal_pet_care.controller;

import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.photo.IPhotoService;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(UrlMapping.PHOTOS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhotoController {
    IPhotoService iPhotoService;

    @PostMapping(UrlMapping.UPLOAD_PHOTO)
    public ResponseEntity<ApiResponse> uploadPhoto(@RequestParam MultipartFile file,
                                                   @RequestParam long userId) throws IOException {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iPhotoService.savePhoto(file, userId))
                .message(FeedbackMessage.CREATE_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_PHOTO_BY_ID)
    public ResponseEntity<ApiResponse> getPhotoById(@PathVariable long id){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(iPhotoService.getPhotoById(id))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(UrlMapping.UPDATE_PHOTO)
    public ResponseEntity<ApiResponse> updatePhoto(@RequestParam MultipartFile file,
                                                   @PathVariable long id) throws IOException {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.UPDATE_SUCCESS)
                .data(iPhotoService.updatePhoto(file, id))
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping(UrlMapping.DELETE_PHOTO)
    public ResponseEntity<ApiResponse> deletePhoto(@PathVariable long photoId, @PathVariable long userId){
        iPhotoService.deletePhoto(photoId, userId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.DELETE_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }
}
