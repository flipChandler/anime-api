package academy.devdojo.animeapi.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "anime")
public class Anime {

    @EqualsAndHashCode.Include()
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "field name can not be empty")
    private String name;
}
