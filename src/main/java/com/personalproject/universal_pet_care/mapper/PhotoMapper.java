package com.personalproject.universal_pet_care.mapper;

import com.personalproject.universal_pet_care.dto.PhotoDTO;
import com.personalproject.universal_pet_care.entity.Photo;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PhotoMapper {
    PhotoDTO toPhotoDTO(Photo photo);
}
