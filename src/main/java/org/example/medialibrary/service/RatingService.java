package org.example.medialibrary.service;

import org.springframework.stereotype.Service;

@Service
public class RatingService {

    public String getFilmPackRating(long like, long dislike) {

        long diff = like - dislike;
        long total = like + dislike;

        if (diff == 0) {
            return "2.50";
        }

        double relativeRating = (double) diff / total;
        double rating = relativeRating * 5;

        rating += 2.5;

        if (rating < 0) {
            rating = 0;
        } else if (rating > 5) {
            rating = 5;
        }

        return String.format("%.2f",rating);
    }

    public double getDoubleFilmPackRating(long like, long dislike) {

        long diff = like - dislike;
        long total = like + dislike;

        if (diff == 0) {
            return 2.5;
        }

        double relativeRating = (double) diff / total;
        double rating = relativeRating * 5;

        rating += 2.5;

        if (rating < 0) {
            rating = 0;
        } else if (rating > 5) {
            rating = 5;
        }

        return rating;
    }
}
