package org.example.medialibrary.service;

import org.example.medialibrary.entity.AppUser;
import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.Pack;
import org.example.medialibrary.entity.dto.FilmDTO;
import org.example.medialibrary.entity.dto.PackDto;
import org.example.medialibrary.repository.FilmRepository;
import org.example.medialibrary.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackService {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RatingService ratingService;

    // Получить все пакеты
    public List<Pack> getAllPacks() {
        return packRepository.findAll();
    }

    @Transactional
    public List<Pack> getAllPacksByUser(AppUser user) {
        return packRepository.findAllByUser(user);
    }

    // Получить пакет по ID
    public Optional<Pack> getPackById(Long id) {
        return packRepository.findById(id);
    }

    // Создать новый пакет
    public Pack createPack(Pack pack) {
        return packRepository.save(pack);
    }

//    public List<Pack> getAccessibleToAllPacks() {
//        return packRepository.findByAccessibleToAllTrue();
//    }
//
//    public List<Pack> getLimitedAccessiblePacks(int limit) {
//        Pageable pageable = PageRequest.of(0, limit); // Задаём лимит количества пакетов
//        return packRepository.findByAccessibleToAllTrue(pageable);
//    }

    @Transactional
    public Optional<Pack> findPackByNameAndUser(String name, AppUser user) {
        return packRepository.findByNameAndUser(name, user);
    }

    // Обновить пакет
    @Transactional
    public Pack updatePack(Long id, Pack updatedPack) {
        return packRepository.findById(id)
                .map(pack -> {
                    pack.setName(updatedPack.getName());
                    pack.setDescription(updatedPack.getDescription());
                    pack.setAccessibleToAll(updatedPack.isAccessibleToAll());
                    pack.setAccessibleToFriends(updatedPack.isAccessibleToFriends());
                    pack.setFilms(updatedPack.getFilms());
                    pack.setUser(updatedPack.getUser());
                    pack.setImage(updatedPack.getImage());
                    return packRepository.save(pack);
                })
                .orElseThrow(() -> new RuntimeException("Pack not found with id " + id));
    }

    // Удалить пакет по ID
    public void deletePack(Long id) {
        packRepository.deleteById(id);
    }

    // Увеличить лайки
    @Transactional
    public void incrementLikes(Long packId) {
        packRepository.findById(packId).ifPresent(pack -> {
            pack.setLike(pack.getLike() + 1);
            packRepository.save(pack);
        });
    }

    // Увеличить дизлайки
    @Transactional
    public void incrementDislikes(Long packId) {
        packRepository.findById(packId).ifPresent(pack -> {
            pack.setDislike(pack.getDislike() + 1);
            packRepository.save(pack);
        });
    }

    // Изменить доступность пакета
    @Transactional
    public void setAccessibility(Long packId, boolean accessibleToAll, boolean accessibleToFriends) {
        packRepository.findById(packId).ifPresent(pack -> {
            pack.setAccessibleToAll(accessibleToAll);
            pack.setAccessibleToFriends(accessibleToFriends);
            packRepository.save(pack);
        });
    }

    // Добавить фильм в пакет
    @Transactional
    public void addFilmToPack(Long packId, Film film) {
        packRepository.findById(packId).ifPresent(pack -> {
            pack.getFilms().add(film);
            packRepository.save(pack);
        });
    }

    // Удалить фильм из пакета
    @Transactional
    public void removeFilmFromPack(Long packId, Film film) {
        packRepository.findById(packId).ifPresent(pack -> {
            pack.getFilms().remove(film);
            packRepository.save(pack);
        });
    }


    @Autowired
    private FilmService filmService;

    @Transactional
    public List<FilmDTO> getFilmsFromPack(Pack pack) {
        List<Film> films = pack.getFilms();

        return films.stream()
                .map(this::convertToFilmDTO)
                .collect(Collectors.toList());
    }

    private FilmDTO convertToFilmDTO(Film film) {
        return filmService.convertToDTO(film);
    }


    @Transactional
    public List<PackDto> getLimitedAccessiblePacksDto(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Pack> packs = packRepository.findByAccessibleToAllTrue(pageable);
        return packs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<PackDto> getAllAppUserPacksDto(AppUser user) {
        List<Pack> packs = getAllPacksByUser(user);
        return packs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

//    public List<PackDto> getAllPacksDto(List<Pack> packs) {
//        return packs.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }


    public PackDto convertToDTO(Pack pack)  {
        PackDto packDto = new PackDto();
        packDto.setId(pack.getId());
        packDto.setTitle(pack.getName());
        packDto.setRating(ratingService.getDoubleFilmPackRating(pack.getLike(), pack.getDislike()));

        if (pack.getImage() != null && pack.getImage().length > 0) {
            byte[] imageBytes = pack.getImage();

            String mimeType = imageService.getImageType(imageBytes);

            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            packDto.setImageBase64(base64Image);
            packDto.setMimeType(mimeType);
        }

        return packDto;
    }

}

