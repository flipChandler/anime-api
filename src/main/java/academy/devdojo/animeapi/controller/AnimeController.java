package academy.devdojo.animeapi.controller;

import academy.devdojo.animeapi.domain.Anime;
import academy.devdojo.animeapi.request.AnimePostRequest;
import academy.devdojo.animeapi.request.AnimePutRequest;
import academy.devdojo.animeapi.service.AnimeService;
import academy.devdojo.animeapi.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/animes")
@Log4j2
@RequiredArgsConstructor        // construtor com todos os atributos que são finals
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> findAll() {
        log.info(dateUtil.formatLocalDateTiemToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping("find")
    public ResponseEntity<List<Anime>> findAnimesByName(@RequestParam String name) {
        return ResponseEntity.ok(animeService.findAnimesByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequest animeRequest) {
        return new ResponseEntity<>(animeService.save(animeRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequest animePutRequest) {
        animeService.replace(animePutRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
