package org.example.medialibrary.configuration;

import org.example.medialibrary.entity.*;
import org.example.medialibrary.repository.AppUserRepository;
import org.example.medialibrary.repository.FilmRepository;
import org.example.medialibrary.repository.GenreRepository;
import org.example.medialibrary.repository.PictureRepository;
import org.example.medialibrary.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Configuration
public class DevelopmentProfileDataLoaderConfiguration {

    @Bean
    @Order(2) // Устанавливает порядок, 1 - самый высокий приоритет
    public CommandLineRunner dataGenresLoader(GenreRepository repo) {
        return args -> {
            repo.save(new Genre("Аниме"));
            repo.save(new Genre("Антиутопия"));
            repo.save(new Genre("Артхаус"));
            repo.save(new Genre("Биография"));
            repo.save(new Genre("Боевик"));
            repo.save(new Genre("Вампирский"));
            repo.save(new Genre("Вестерн"));
            repo.save(new Genre("Военный"));
            repo.save(new Genre("Детектив"));
            repo.save(new Genre("Детский"));
            repo.save(new Genre("Документальный"));
            repo.save(new Genre("Драма"));
            repo.save(new Genre("Зомби"));
            repo.save(new Genre("Игра"));
            repo.save(new Genre("Исторический"));
            repo.save(new Genre("Киберпанк"));
            repo.save(new Genre("Комедия"));
            repo.save(new Genre("Комиксы"));
            repo.save(new Genre("Концерт"));
            repo.save(new Genre("Короткометражка"));
            repo.save(new Genre("Криминал"));
            repo.save(new Genre("Литературная фантастика"));
            repo.save(new Genre("Мелодрама"));
            repo.save(new Genre("Мистика"));
            repo.save(new Genre("Музыка"));
            repo.save(new Genre("Мультфильм"));
            repo.save(new Genre("Мюзикл"));
            repo.save(new Genre("Научно-фантастический"));
            repo.save(new Genre("Новости"));
            repo.save(new Genre("Опера"));
            repo.save(new Genre("Постапокалипсис"));
            repo.save(new Genre("Приключения"));
            repo.save(new Genre("Психологический триллер"));
            repo.save(new Genre("Путешествие во времени"));
            repo.save(new Genre("Реальное ТВ"));
            repo.save(new Genre("Ретро"));
            repo.save(new Genre("Романтика"));
            repo.save(new Genre("Романтическая комедия"));
            repo.save(new Genre("Семейный"));
            repo.save(new Genre("Сказка"));
            repo.save(new Genre("Спорт"));
            repo.save(new Genre("Стимпанк"));
            repo.save(new Genre("Ток-шоу"));
            repo.save(new Genre("Триллер"));
            repo.save(new Genre("Фантастика"));
            repo.save(new Genre("Церемония"));
            repo.save(new Genre("Экшн"));
        };
    }

    @Bean
    @Order(3) // Устанавливает порядок, 1 - самый высокий приоритет
    public CommandLineRunner FilmsLoader(FilmRepository repo) {
        return args -> {
            try {

                List<Genre> genres1 = new ArrayList<>();
                genres1.add(new Genre("драма"));
                genres1.add(new Genre("мелодрама"));
                genres1.add(new Genre("комедия"));

                repo.save(new Film(
                        "Консьерж",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/Concierge.webp")),
                        "У Дугласа, консьержа роскошной гостиницы, две мечты - заработать на собственный отель и жениться на красотке Энди. Щедрые чаевые от богатых клиентов помогут осуществить первую. А со второй проблема - как жениться на девушке, у которой уже есть жених, к тому же красивый и богатый?",
                        "США",
                        1993,
                        genres1
                ));

                List<Genre> genres2 = new ArrayList<>();
                genres2.add(new Genre("комедия"));

                repo.save(new Film(
                        "Домоуправ",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/House manager.webp")),
                        "Луи Критский получил в подарок от отца жилой дом. Один из 27 по 1 миллиону долларов каждый - часть его наследства. Отец просит сына только об одном - не исправлять ни единого повреждения и не чинить неисправности. И как назло, суд по жалобе одного из жильцов, выносит решение о наказании Луи за нежелание поддерживать порядок во вверенном ему доме.\n" +
                                "\n" +
                                "Луи вынужден подчиниться судебному приговору - самому жить в доме, которые является его собственностью, без права ремонтировать что-либо в своей квартире, пока не будет отремонтирован весь дом.",
                        "США",
                        1991,
                        genres2
                ));

                List<Genre> genres3 = new ArrayList<>();
                genres3.add(new Genre("драма"));
                genres3.add(new Genre("мелодрама"));
                genres3.add(new Genre("комедия"));

                repo.save(new Film(
                        "Деловая женщина",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/Business woman.webp")),
                        "Предприимчивая Нью-Йоркская машинистка Тесс Мак Гилл мечтает о головокружительной карьере. Но на пути к осуществлению мечты стоит серьезная преграда, Она не умеет одеваться, стричься, общаться с нужными людьми. Но каждому человеку дается шанс устроить свою жизнь. Такой шанс был дан и Мак Гилл, она получила шанс выступить в роли руководителя высшего звена, когда ее начальница сломала ногу, отдыхая в Европе.\n" +
                                "\n" +
                                "Незамедлительно Тесс занимает ее место, знакомится с биржевым брокером и заключает крупную сделку. Когда отдохнувшая и выздоровевшая начальница возвращается на работу, она понимает, что место ее занято, жених ушел к другой, и существует миллион других мелких неприятностей.",
                        "США",
                        1988,
                        genres3
                ));

                List<Genre> genres4 = new ArrayList<>();
                genres4.add(new Genre("мелодрама"));
                genres4.add(new Genre("комедия"));

                repo.save(new Film(
                        "Секрет моего успеха",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/The secret of my success.webp")),
                        "Брантли Фостер — выпускник колледжа из Канзаса — переезжает в Нью-Йорк, чтобы начать карьеру финансиста. Однако компанию, в которой он планировал работать, покупает другая корпорация, и нашему герою отказывают. После нескольких неудачных попыток найти другую работу Брантли устраивается на должность разносчика писем в компанию своего дяди Говарда Прескота.",
                        "США",
                        1987,
                        genres4
                ));

                List<Genre> genres5 = new ArrayList<>();
                genres5.add(new Genre("комедия"));
                genres5.add(new Genre("мелодрама"));

                repo.save(new Film(
                        "10 вещей, которые я в тебе ненавижу",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/10 Things I Hate About You.webp")),
                        "Сюжет основан на пьесе Шекспира и рассказывает о двух сестрах, одна из которых должна быть завоевана, чтобы другая могла начать встречаться.",
                        "США",
                        1999,
                        genres5
                ));

                List<Genre> genres6 = new ArrayList<>();
                genres6.add(new Genre("фантастика"));
                genres6.add(new Genre("экшен"));

                repo.save(new Film(
                        "Матрица",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/The Matrix.webp")),
                        "Компьютерный хакер Нио узнает, что мир, в котором он живет, является симуляцией, созданной машинами.",
                        "США",
                        1999,
                        genres6
                ));


                List<Genre> genres8 = new ArrayList<>();
                genres8.add(new Genre("анимация"));
                genres8.add(new Genre("семейный"));

                repo.save(new Film(
                        "Король Лев",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/The Lion King.webp")),
                        "Молодой лев Симба должен принять свое место в круге жизни после трагической утраты.",
                        "США",
                        1994,
                        genres8
                ));

                List<Genre> genres9 = new ArrayList<>();
                genres9.add(new Genre("комедия"));
                genres9.add(new Genre("криминал"));

                repo.save(new Film(
                        "Криминальное чтиво",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/Pulp Fiction.webp")),
                        "Несколько пересекающихся историй о преступности и насилии в Лос-Анджелесе.",
                        "США",
                        1994,
                        genres9
                ));

                List<Genre> genres10 = new ArrayList<>();
                genres10.add(new Genre("мелодрама"));
                genres10.add(new Genre("драма"));

                repo.save(new Film(
                        "Титаник",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/Titanic.webp")),
                        "История любви между молодым художником и аристократкой на фоне трагедии крушения Титаника.",
                        "США",
                        1997,
                        genres10
                ));


                List<Genre> genres11 = new ArrayList<>();
                genres11.add(new Genre("боевик"));
                genres11.add(new Genre("триллер"));

                repo.save(new Film(
                        "Крепкий орешек",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/Die Hard.webp")),
                        "Полицейский Джон Мактейн оказывается в центре захвата заложников в небоскребе, где он должен спасти людей и остановить террористов.",
                        "США",
                        1988,
                        genres11
                ));

                List<Genre> genres12 = new ArrayList<>();
                genres12.add(new Genre("приключение"));
                genres12.add(new Genre("фантастика"));

                repo.save(new Film(
                        "Индиана Джонс: В поисках утраченного ковчега",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/Raiders of the Lost Ark.webp")),
                        "Археолог и искатель приключений Индиана Джонс отправляется на поиски ковчега завета, соперничая с нацистами.",
                        "США",
                        1981,
                        genres12
                ));

                List<Genre> genres13 = new ArrayList<>();
                genres13.add(new Genre("драма"));
                genres13.add(new Genre("детектив"));
                genres13.add(new Genre("ужасы"));
                genres13.add(new Genre("триллер"));

                repo.save(new Film(
                        "Сияние",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/The Shining.webp")),
                        "Писатель Джек Торренс становится смотрителем отеля и постепенно теряет рассудок, оказываясь под влиянием сверхъестественных сил.",
                        "США",
                        1980,
                        genres13
                ));

                List<Genre> genres14 = new ArrayList<>();
                genres14.add(new Genre("драма"));
                genres14.add(new Genre("биография"));

                repo.save(new Film(
                        "Социальная сеть",
                        Files.readAllBytes(Paths.get("src/main/resources/development/film/image/The Social Network.webp")),
                        "История создания Facebook и юридических конфликтов, связанных с его основателем Марком Цукербергом.",
                        "США",
                        2010,
                        genres14
                ));

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    @Order(3) // Устанавливает порядок, 1 - самый высокий приоритет
    public CommandLineRunner PicturesLoader(PictureRepository repo) {
        return args -> {
            try {
                repo.save(new Picture("no_image_v1", Files.readAllBytes(Paths.get("src/main/resources/static/images/voidProfile1.jpg"))));
                repo.save(new Picture("default_film_pack", Files.readAllBytes(Paths.get("src/main/resources/static/images/AllFilmsPack.jpg"))));
                repo.save(new Picture(Files.readAllBytes(Paths.get("src/main/resources/static/images/voidProfile2.jpg"))));
                repo.save(new Picture(Files.readAllBytes(Paths.get("src/main/resources/static/images/voidProfile3.jpg"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }


    @Bean
    @Order(4) // Устанавливает порядок, 1 - самый высокий приоритет
    public CommandLineRunner UserLoader(AppUserService repo) {
        return args -> {
            AppUser user1 = new AppUser();
            AppUser user2 = new AppUser();

            user1.setUsername("Maks");
            user1.setEmail("Maks@gmail.com");
            user1.setPassword("123456");

            user2.setUsername("Gleb");
            user2.setEmail("Gleb@gmail.com");
            user2.setPassword("123456");

            repo.registerUser(user1);
            repo.registerUser(user2);
        };
    }

    @Bean
    @Order(5) // Устанавливает порядок, 1 - самый высокий приоритет
    public CommandLineRunner PackLoader(PackService repo,
                                        AppUserRepository userRepository,
                                        AppPropertiesConfiguration prop,
                                        PictureService pictureService) {
        return args -> {

            String lorem = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

            Optional<AppUser> user1 = userRepository.findByUsername("Maks");
            Optional<AppUser> user2 = userRepository.findByUsername("Gleb");

            if (user1.isPresent()) {

                AppUser u1 = user1.get();

                Pack u1Default = new Pack();
                u1Default.setName(prop.getAllFilmPackName());
                u1Default.setDescription(prop.getAllFilmPackDescription());
                u1Default.setAccessibleToAll(false);
                u1Default.setAccessibleToFriends(false);
                u1Default.setUser(u1);

                Pack u1Pack1 = new Pack();
                Pack u1Pack2 = new Pack();
                Pack u1Pack3 = new Pack();


                u1Pack1.setName("Макс пак 1");
                u1Pack1.setDescription(lorem);
                u1Pack1.setAccessibleToAll(true);
                u1Pack1.setAccessibleToFriends(true);
                u1Pack1.setUser(u1);

                u1Pack2.setName("Макс пак 2");
                u1Pack2.setDescription(lorem);
                u1Pack2.setAccessibleToAll(false);
                u1Pack2.setAccessibleToFriends(true);
                u1Pack2.setUser(u1);

                u1Pack3.setName("Макс пак 3");
                u1Pack3.setDescription(lorem);
                u1Pack3.setAccessibleToAll(true);
                u1Pack3.setAccessibleToFriends(true);
                u1Pack3.setUser(u1);

                try {
                    u1Pack1.setImage(Files.readAllBytes(Paths.get("src/main/resources/development/film/image/toPack/1to1.jpg")));
                    u1Pack2.setImage(Files.readAllBytes(Paths.get("src/main/resources/development/film/image/toPack/2to1.jpg")));
                    u1Pack3.setImage(Files.readAllBytes(Paths.get("src/main/resources/development/film/image/toPack/3to1.jpg")));

                    Optional<Picture> pictureOptional = pictureService.getPictureByName(prop.getAllFilmPackImage());
                    pictureOptional.ifPresent(picture -> u1Default.setImage(picture.getData()));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                repo.createPack(u1Default);
                repo.createPack(u1Pack1);
                repo.createPack(u1Pack2);
                repo.createPack(u1Pack3);

            }

            if (user2.isPresent()) {
                AppUser u2 = user2.get();


                Pack u2Default = new Pack();
                u2Default.setName(prop.getAllFilmPackName());
                u2Default.setDescription(prop.getAllFilmPackDescription());
                u2Default.setAccessibleToAll(false);
                u2Default.setAccessibleToFriends(false);
                u2Default.setUser(u2);


                Pack u2Pack1 = new Pack();
                Pack u2Pack2 = new Pack();
                Pack u2Pack3 = new Pack();


                u2Pack1.setName("Глеб пак 1");
                u2Pack1.setDescription(lorem);
                u2Pack1.setAccessibleToAll(true);
                u2Pack1.setAccessibleToFriends(true);
                u2Pack1.setUser(u2);

                u2Pack2.setName("Глеб пак 2");
                u2Pack2.setDescription(lorem);
                u2Pack2.setAccessibleToAll(false);
                u2Pack2.setAccessibleToFriends(true);
                u2Pack2.setUser(u2);

                u2Pack3.setName("Глеб пак 3");
                u2Pack3.setDescription(lorem);
                u2Pack3.setAccessibleToAll(true);
                u2Pack3.setAccessibleToFriends(true);
                u2Pack3.setUser(u2);

                try {
                    u2Pack1.setImage(Files.readAllBytes(Paths.get("src/main/resources/development/film/image/toPack/1to2.jpg")));
                    u2Pack2.setImage(Files.readAllBytes(Paths.get("src/main/resources/development/film/image/toPack/2to2.jpg")));
                    u2Pack3.setImage(Files.readAllBytes(Paths.get("src/main/resources/development/film/image/toPack/3to2.jpg")));

                    Optional<Picture> pictureOptional = pictureService.getPictureByName(prop.getAllFilmPackImage());
                    pictureOptional.ifPresent(picture -> u2Default.setImage(picture.getData()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                repo.createPack(u2Default);
                repo.createPack(u2Pack1);
                repo.createPack(u2Pack2);
                repo.createPack(u2Pack3);

            }
        };
    }

    @Bean
    @Order(6) // Устанавливает порядок, 1 - самый высокий приоритет
    public CommandLineRunner FilmToPackLoader(PackService packRepo, FilmService filmRepo) {
        return args -> {

            packRepo.addFilmToPack(1L, filmRepo.getFilmById(1L).get());
            packRepo.addFilmToPack(1L, filmRepo.getFilmById(2L).get());
            packRepo.addFilmToPack(1L, filmRepo.getFilmById(3L).get());
            packRepo.addFilmToPack(1L, filmRepo.getFilmById(4L).get());
            packRepo.addFilmToPack(1L, filmRepo.getFilmById(5L).get());
            packRepo.addFilmToPack(1L, filmRepo.getFilmById(6L).get());
            packRepo.addFilmToPack(1L, filmRepo.getFilmById(7L).get());

            packRepo.addFilmToPack(2L, filmRepo.getFilmById(1L).get());
            packRepo.addFilmToPack(2L, filmRepo.getFilmById(2L).get());
            packRepo.addFilmToPack(2L, filmRepo.getFilmById(3L).get());

            packRepo.addFilmToPack(3L, filmRepo.getFilmById(3L).get());
            packRepo.addFilmToPack(3L, filmRepo.getFilmById(4L).get());
            packRepo.addFilmToPack(3L, filmRepo.getFilmById(5L).get());
            packRepo.addFilmToPack(3L, filmRepo.getFilmById(6L).get());

            packRepo.addFilmToPack(4L, filmRepo.getFilmById(5L).get());
            packRepo.addFilmToPack(4L, filmRepo.getFilmById(6L).get());
            packRepo.addFilmToPack(4L, filmRepo.getFilmById(7L).get());




            packRepo.addFilmToPack(5L, filmRepo.getFilmById(8L).get());
            packRepo.addFilmToPack(5L, filmRepo.getFilmById(9L).get());
            packRepo.addFilmToPack(5L, filmRepo.getFilmById(10L).get());
            packRepo.addFilmToPack(5L, filmRepo.getFilmById(11L).get());
            packRepo.addFilmToPack(5L, filmRepo.getFilmById(12L).get());
            packRepo.addFilmToPack(5L, filmRepo.getFilmById(13L).get());

            packRepo.addFilmToPack(6L, filmRepo.getFilmById(8L).get());
            packRepo.addFilmToPack(6L, filmRepo.getFilmById(9L).get());
            packRepo.addFilmToPack(6L, filmRepo.getFilmById(10L).get());


            packRepo.addFilmToPack(7L, filmRepo.getFilmById(10L).get());
            packRepo.addFilmToPack(7L, filmRepo.getFilmById(11L).get());
            packRepo.addFilmToPack(7L, filmRepo.getFilmById(12L).get());

            packRepo.addFilmToPack(8L, filmRepo.getFilmById(11L).get());
            packRepo.addFilmToPack(8L, filmRepo.getFilmById(12L).get());
            packRepo.addFilmToPack(8L, filmRepo.getFilmById(13L).get());
        };
    }


    @Bean
    @Order(7) // Устанавливает порядок, 1 - самый высокий приоритет
    public CommandLineRunner CountryLoader(CountryService repo) {
        return args -> {
            repo.createCountry("США");
            repo.createCountry("Индия");
            repo.createCountry("Китай");
            repo.createCountry("Япония");
            repo.createCountry("Южная Корея");
            repo.createCountry("Франция");
            repo.createCountry("Германия");
            repo.createCountry("Великобритания");
            repo.createCountry("Италия");
            repo.createCountry("Россия");
            repo.createCountry("Другая");
        };
    }


//    addFilmToPack

    /*
    США (Голливуд)
Индия (Болливуд и другие региональные индустрии)
Китай
Япония
Южная Корея
Франция
Германия
Великобритания
Италия
Испания
Бразилия
Мексика
Россия (наследие СССР)
СССР (Советский Союз)
Тайвань
Австралия
Новая Зеландия
Швеция
Норвегия
Дания
Филиппины
Аргентина
Южноафриканская Республика
Иран
Саудовская Аравия
Греция
Польша
Чехия
Канада
Сингапур
Турция
Другая
     */

}
