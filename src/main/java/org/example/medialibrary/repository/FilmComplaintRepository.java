package org.example.medialibrary.repository;

import org.example.medialibrary.entity.FilmComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmComplaintRepository extends JpaRepository<FilmComplaint, Long> {
}
