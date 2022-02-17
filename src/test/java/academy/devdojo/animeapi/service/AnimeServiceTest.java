package academy.devdojo.animeapi.service;

import academy.devdojo.animeapi.domain.Anime;
import academy.devdojo.animeapi.exception.BadRequestException;
import academy.devdojo.animeapi.repository.AnimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static academy.devdojo.animeapi.helper.AnimeHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepository;

    @BeforeEach
    void setup() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(createValidAnime()));

        when(animeRepository.findAll(any(PageRequest.class))).thenReturn(animePage);

        when(animeRepository.findAll()).thenReturn(List.of(createValidAnime()));

        when(animeRepository.findById(anyLong())).thenReturn(Optional.of(createValidAnime()));

        when(animeRepository.findAnimesByName(anyString())).thenReturn(List.of(createValidAnime()));

        when(animeRepository.save(any(Anime.class))).thenReturn(createValidAnime());

        doNothing().when(animeRepository).delete(any(Anime.class));
    }

    @Test
    @DisplayName("findAll returns list of animes inside page object when sucessful")
    void findAll_returnsListOfAnimesInsidePageObject_whenSuccessful() {
        // given
        var expectedName = createValidAnime().getName();

        // when
        var result = animeService.findAll(PageRequest.of(0, 10));

        // then
        assertThat(result).isNotNull();
        assertThat(result.toList()).isNotEmpty().hasSize(1);
        assertThat(result.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findAll returns list of animes when sucessful")
    void findAll_returnsListOfAnimes_whenSuccessful() {
        // given
        var expectedName = createValidAnime().getName();

        // when
        var result = animeService.findAll();

        // then
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(result.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns anime when sucessful")
    void findByIdOrThrowBadRequestException_returnsAnime_whenSuccessful() {
        // given
        var expectedId = createValidAnime().getId();

        // when
        var result = animeService.findByIdOrThrowBadRequestException(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws exception when anime is not found")
    void findByIdOrThrowBadRequestException_throwsException_whenAnimeIsNotFound() {
        // given
        when(animeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> animeService.findByIdOrThrowBadRequestException(10L))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Anime not found");
    }

    @Test
    @DisplayName("findAnimesByName returns list of animes when sucessful")
    void findAnimesByName_returnsListOfAnimes_whenSuccessful() {
        // given
        var expectedName = createValidAnime().getName();

        // when
        var result = animeService.findAnimesByName("Anime");

        // then
        assertThat(result).isNotNull();
        assertThat(result.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findAnimesByName returns empty list when anime is not found")
    void findAnimesByName_returnsEmptyList_whenAnimeIsNotFound() {
        // given
        when(animeRepository.findAnimesByName(anyString())).thenReturn(Collections.emptyList());

        // when
        var result = animeService.findAnimesByName("Not Found");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("save returns anime when sucessful")
    void save_returnsAnime_whenSuccessful() {
        // when
        var result = animeService.save(createAnimePostRequest());

        // then
        assertThat(result).isNotNull()
                .isEqualTo(createValidAnime());
    }

    @Test
    @DisplayName("replace updates anime when sucessful")
    void replace_updatesAnime_whenSuccessful() {
        // when
        // then
        assertThatCode(() -> animeService.replace(createAnimePutRequest()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes anime when sucessful")
    void delete_removesAnime_whenSuccessful() {
        // when
        // then
        assertThatCode(() -> animeService.delete(1L))
                .doesNotThrowAnyException();
    }
}