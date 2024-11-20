package org.example.medialibrary.service;

import org.example.medialibrary.entity.FilmComplaint;
import org.example.medialibrary.repository.FilmComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FilmComplaintService {
    public static final String FILM_COMPLAINT = "Film Complaint";

    @Autowired
    private FilmComplaintRepository filmComplaintRepository;

    public void filmComplaint(String message, Long sourceId, Long resourceId) {
        FilmComplaint filmComplaint = new FilmComplaint(message, sourceId, resourceId);
        filmComplaintRepository.save(filmComplaint);
    }
}
