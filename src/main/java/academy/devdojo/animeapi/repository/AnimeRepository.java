package academy.devdojo.animeapi.repository;

import academy.devdojo.animeapi.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findAnimesByName(String name);
}
