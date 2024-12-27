package com.personalproject.universal_pet_care.service.photo;

import com.personalproject.universal_pet_care.dto.PhotoDTO;
import com.personalproject.universal_pet_care.entity.Photo;
import com.personalproject.universal_pet_care.entity.User;
import com.personalproject.universal_pet_care.exception.AppException;
import com.personalproject.universal_pet_care.exception.ErrorCode;
import com.personalproject.universal_pet_care.mapper.PhotoMapper;
import com.personalproject.universal_pet_care.repository.PhotoRepository;
import com.personalproject.universal_pet_care.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PhotoServiceImp implements PhotoService {
    PhotoRepository photoRepository;
    PhotoMapper photoMapper;
    UserRepository userRepository;

    @Override
    public PhotoDTO savePhoto(MultipartFile file, long userId) throws IOException {
        Photo photo = Photo.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .image(file.getBytes())
                .build();

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        user.setPhoto(photo);
        photoRepository.save(photo);
        userRepository.save(user);

        return photoMapper.toPhotoDTO(photo);
    }

    @Override
    public PhotoDTO getPhotoById(long id)
    {
        return photoMapper.toPhotoDTO(photoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND)));
    }

    @Transactional
    @Override
    public void deletePhoto(long photoId, long userId)
    {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        user.setPhoto(null);
        userRepository.save(user);

        photoRepository.deleteById(photoId);
    }

    @Override
    public PhotoDTO updatePhoto(MultipartFile file, long id) throws IOException {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_DATA_FOUND));
        photo.setFileName(file.getOriginalFilename());
        photo.setFileType(file.getContentType());
        photo.setImage(file.getBytes());

        return photoMapper.toPhotoDTO(photoRepository.save(photo));
    }
}
