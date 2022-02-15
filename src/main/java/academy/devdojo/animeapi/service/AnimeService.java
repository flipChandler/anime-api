package academy.devdojo.animeapi.service;

import academy.devdojo.animeapi.domain.Anime;
import academy.devdojo.animeapi.mapper.AnimeMapper;
import academy.devdojo.animeapi.repository.AnimeRepository;
import academy.devdojo.animeapi.request.AnimePostRequest;
import academy.devdojo.animeapi.request.AnimePutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Anime findByIdOrThrowBadRequestException(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Anime not found"));
    }

    @Transactional
    public Anime save(AnimePostRequest animePostRequest) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequest));
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequest animePutRequest) {
        findByIdOrThrowBadRequestException(animePutRequest.getId());
        var anime = AnimeMapper.INSTANCE.toAnime(animePutRequest);
        anime.setId(animePutRequest.getId());
        animeRepository.save(anime);
    }

    /*
    private static List<Anime> animes;

    static {
        animes = new ArrayList<>(List.of(new Anime(1L, "DBZ"), new Anime(2L, "Berserk")));
    }

    public List<Anime> findAll() {
        return animes;
    }

    public Anime findById(Long id) {
        return animes.stream().filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Anime not found"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3, 10000));
        animes.add(anime);
        return anime;
    }

    public void delete(Long id) {
        animes.remove(findById(id));
    }

    public void replace(Anime anime) {
        delete(anime.getId());
        animes.add(anime);
    }
     */
}
