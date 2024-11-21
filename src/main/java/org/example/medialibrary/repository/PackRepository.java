package org.example.medialibrary.repository;


import org.example.medialibrary.entity.AppUser;
import org.example.medialibrary.entity.Pack;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    List<Pack> findByAccessibleToAllTrue();

    List<Pack> findByAccessibleToAllTrue(Pageable pageable);

    List<Pack> findAllByUser(AppUser user);

    Optional<Pack> findByNameAndUser(String name, AppUser user);

    boolean existsByIdAndUserId(Long packId, Long userId);

    boolean existsByUserIdAndFilmsId(Long userId, Long filmId);
}
