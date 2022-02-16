package academy.devdojo.animeapi.service;

import academy.devdojo.animeapi.domain.Anime;
import academy.devdojo.animeapi.exception.BadRequestException;
import academy.devdojo.animeapi.mapper.AnimeMapper;
import academy.devdojo.animeapi.repository.AnimeRepository;
import academy.devdojo.animeapi.request.AnimePostRequest;
import academy.devdojo.animeapi.request.AnimePutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> findAll() {
        return animeRepository.findAll();
    }

    public List<Anime> findAnimesByName(String name) {
        return animeRepository.findAnimesByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }
    // tendo um método que lança Exception (checked), só Transactional não faz o Rollback, precisa passar o rollbackFor = Exception.class
    @Transactional(rollbackFor = Exception.class)
    public Anime save(AnimePostRequest animePostRequest) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequest));
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    @Transactional(rollbackFor = Exception.class)
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
