package academy.devdojo.animeapi.client;

import academy.devdojo.animeapi.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {

        // RestTemplate faz calls http direto
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8081/api/v1/animes/1", Anime.class);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8081/api/v1/animes/{id}", Anime.class, 1);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8081/api/v1/animes", Anime[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8081/api/v1/animes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {
                });
        log.info(exchange.getBody());
    }
}