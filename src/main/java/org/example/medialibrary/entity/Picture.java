package org.example.medialibrary.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Lob
    private byte[] data;

    public Picture(byte[] data) {
        this.data = data;
    }

    public Picture(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }
}
