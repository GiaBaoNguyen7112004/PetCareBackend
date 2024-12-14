package com.personalproject.universal_pet_care.service.photo;

import com.personalproject.universal_pet_care.dto.PhotoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    PhotoDTO savePhoto(MultipartFile file, long userId) throws IOException;

    PhotoDTO getPhotoById(long id);

    void deletePhoto(long photoId, long userId);

    PhotoDTO updatePhoto(MultipartFile file, long id) throws IOException;
}
