package academy.devdojo.animeapi.helper;

import academy.devdojo.animeapi.domain.Anime;
import academy.devdojo.animeapi.request.AnimePostRequest;
import academy.devdojo.animeapi.request.AnimePutRequest;

public class AnimeHelper {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Anime Um")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .id(1L)
                .name("Anime Um")
                .build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .id(1L)
                .name("Anime Um")
                .build();
    }

    public static AnimePostRequest createAnimePostRequest(){
        return AnimePostRequest.builder()
                .name(createAnimeToBeSaved().getName())
                .build();
    }

    public static AnimePutRequest createAnimePutRequest(){
        return AnimePutRequest.builder()
                .id(createValidUpdatedAnime().getId())
                .name(createValidUpdatedAnime().getName())
                .build();
    }
}
