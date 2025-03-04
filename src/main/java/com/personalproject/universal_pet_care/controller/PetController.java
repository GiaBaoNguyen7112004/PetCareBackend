package com.personalproject.universal_pet_care.controller;


import com.personalproject.universal_pet_care.payload.request.pet.PetUpdatingRequest;
import com.personalproject.universal_pet_care.payload.response.ApiResponse;
import com.personalproject.universal_pet_care.service.pet.PetService;
import com.personalproject.universal_pet_care.utils.FeedbackMessage;
import com.personalproject.universal_pet_care.utils.UrlMapping;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.PETS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PetController {
    PetService petService;

    @GetMapping(UrlMapping.GET_ALL_PETS)
    public ResponseEntity<ApiResponse> getAllPets() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(petService.getAllPets())
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping(UrlMapping.GET_PET_BY_ID)
    public ResponseEntity<ApiResponse> getPetById(@PathVariable long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(petService.getPetById(id))
                .message(FeedbackMessage.GET_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(UrlMapping.UPDATE_PET)
    public ResponseEntity<ApiResponse> updatePet(@PathVariable long id,
                                                 @RequestBody @Valid PetUpdatingRequest petUpdatingRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(petService.updatePet(id, petUpdatingRequest))
                .message(FeedbackMessage.UPDATE_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping(UrlMapping.DELETE_PET)
    public ResponseEntity<ApiResponse> getAllPets(@PathVariable long id) {
        petService.deletePet(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message(FeedbackMessage.DELETE_SUCCESS)
                .build();

        return ResponseEntity.ok().body(apiResponse);
    }
}
