package org.example.medialibrary.service;

import org.springframework.stereotype.Service;

@Service
public class RatingService {

    /*
        Чтобы сформировать рейтинг по 10-бальной шкале на основе лайков и дизлайков, можно использовать следующую формулу:

        Определите общее количество лайков и дизлайков:
        Лайки = L
        Дизлайки = D

        Рассчитайте чистый рейтинг (чистые лайки):
        Чистые лайки = L - D

        Определите общее количество голосов:
        Общее количество голосов = L + D

        Рассчитайте относительный рейтинг:
        Относительный рейтинг = Чистые лайки / Общее количество голосов

        Преобразуйте относительный рейтинг в 10-балльную шкалу:
        Рейтинг = Относительный рейтинг × 10

        Округлить до 2 знаков после запятой

        Убедитесь, что рейтинг находится в пределах от 0 до 10:
        Если Рейтинг < 0, то Рейтинг = 0
        Если Рейтинг > 10, то Рейтинг = 10
     */

    public String getFilmPackRating(long like, long dislike) {

        long diff = dislike - like;
        long total = like + dislike;

        if (diff == 0) {
            return "2.50";
        }

        double relativeRating = (double) diff / total;
        double rating = relativeRating * 5;

        if (rating < 0) {
            rating = 0;
        } else if (rating > 5) {
            rating = 5;
        }

        return String.format("%.2f",rating);
    }

    public double getDoubleFilmPackRating(long like, long dislike) {

        long diff = dislike - like;
        long total = like + dislike;

        if (diff == 0) {
            return 2.5;
        }

        double relativeRating = (double) diff / total;
        double rating = relativeRating * 5;

        if (rating < 0) {
            rating = 0;
        } else if (rating > 5) {
            rating = 5;
        }

        return rating;
    }
}
