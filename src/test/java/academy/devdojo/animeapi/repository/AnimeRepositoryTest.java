package academy.devdojo.animeapi.repository;

import academy.devdojo.animeapi.domain.Anime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
public class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when succesfull")
    void save_persistsAnime_whenSuccessful() {
        // given
        var anime = buildAnime();

        // when
        var result = animeRepository.save(anime);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(anime.getName());
    }

    @Test
    @DisplayName("Save updates anime when succesfull")
    void save_updatesAnime_whenSuccessful() {
        // given
        var anime = buildAnime();
        var savedAnime = animeRepository.save(anime);
        savedAnime.setName("Anime Zero Atualizado");

        // when
        var result = animeRepository.save(savedAnime);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(savedAnime.getId());
        assertThat(result.getName()).isEqualTo(savedAnime.getName());
    }

    @Test
    @DisplayName("Delete removes anime when succesfull")
    void delete_removesAnime_whenSuccessful() {
        // given
        var anime = buildAnime();
        var savedAnime = animeRepository.save(anime);

        // when
        animeRepository.delete(savedAnime);
        var result = animeRepository.findById(savedAnime.getId());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Find Animes By Name returns anime when succesfull")
    void findAnimesByName_removesAnime_whenSuccessful() {
        // given
        var animes = buildAnimes();
        animeRepository.saveAll(animes);
        var anime3 = animeRepository.findById(3L);

        // when
        var result = animeRepository.findAnimesByName(anime3.get().getName());

        // then
        assertThat(result).isNotEmpty()
                .contains(anime3.get());
    }

    @Test
    @DisplayName("Find Animes By Name returns empty list when no anime is found")
    void findAnimesByName_returnsEmptyList_whenAnimeIsNotFound() {
        // given
        // when
        var result = animeRepository.findAnimesByName("Name does not exist");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Save throws Constraint Violation Exception when name is empty 1")
    void save_throwsConstraintViolationException_whenNameIsEmpty1() {
        // given
        var anime = new Anime();

        // when
        // then
        assertThatThrownBy(() -> animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("field name can not be empty");
    }

    @Test
    @DisplayName("Save throws Constraint Violation Exception when name is empty 2")
    void save_throwsConstraintViolationException_whenNameIsEmpty2() {
        // given
        var anime = new Anime();

        // when
        // then
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> animeRepository.save(anime))
                .withMessageContaining("field name can not be empty");
    }

    private Anime buildAnime() {
        return Anime.builder()
                .name("Anime Um")
                .build();
    }

    private static List<Anime> buildAnimes() {
        var anime1 = Anime.builder()
                .name("Anime Um")
                .build();

        var anime2 = Anime.builder()
                .name("Anime Dois")
                .build();

        var anime3 = Anime.builder()
                .name("Anime Tres")
                .build();

        return List.of(anime1, anime2, anime3);
    }
}