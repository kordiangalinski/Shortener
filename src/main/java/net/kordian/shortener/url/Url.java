package net.kordian.shortener.url;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int authorId;
    private String source;

    @Column(unique = true)
    private String destination;

    private int count;
}
