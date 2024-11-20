package org.example.medialibrary.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "media.library.film")
public class AppPropertiesConfiguration {

    private String noImage;

    private String allFilmPackImage;
    private String allFilmPackName = "Весь список";
    private String allFilmPackDescription = "Здесь находятся все созданные вами фильмы";

    private String homePackFilmListTitle = "Подборки фильмов";
    private String userPackFilmListTitle = "Мои подборки фильмов";
}
