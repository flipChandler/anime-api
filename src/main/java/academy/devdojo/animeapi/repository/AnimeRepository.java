package academy.devdojo.animeapi.repository;

import academy.devdojo.animeapi.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
