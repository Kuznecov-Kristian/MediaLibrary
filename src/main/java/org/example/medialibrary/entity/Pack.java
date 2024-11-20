package org.example.medialibrary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;                  // Название пакета
    @Lob
    private String description;           // Описание пакета

    @Column(name = "likes")
    private long like = 1000;                    // Оценка пакета

    private long dislike = 1000;                 // Оценка пакета
    @Getter
    private boolean accessibleToAll;      // Доступен для всех
    private boolean accessibleToFriends;  // Доступен для друзей

    @Lob
    private byte[] image;

    @ManyToMany(fetch = FetchType.LAZY) // fetch as needed
    @JoinTable(
            name = "pack_films",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> films = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user; // Владелец пакета

    public Pack(String name, String description, boolean accessibleToAll, boolean accessibleToFriends) {
        this.name = name;
        this.description = description;
        this.accessibleToAll = accessibleToAll;
        this.accessibleToFriends = accessibleToFriends;
    }


}

