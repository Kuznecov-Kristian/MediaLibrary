package org.example.medialibrary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "complaints")
public class FilmComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "complaint_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime eventDate;

    private String complaint;

    private Long sourceId;
    private Long resourceId;


    public FilmComplaint(String complaint, Long sourceId, Long resourceId) {
        eventDate = LocalDateTime.now();
        this.complaint = complaint;
        this.sourceId = sourceId;
        this.resourceId = resourceId;
    }
}
