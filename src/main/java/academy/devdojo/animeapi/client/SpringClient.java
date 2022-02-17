package academy.devdojo.animeapi.client;

import academy.devdojo.animeapi.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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


        Anime anime2 = Anime.builder().name("Hunter x Hunter").build();
        Anime savedAnime2 = new RestTemplate().postForObject("http://localhost:8081/api/v1/animes", anime2, Anime.class);
        log.info("saved anime {}", savedAnime2);

        Anime anime3 = Anime.builder().name("One Punchman").build();
        ResponseEntity<Anime> savedAnime3 = new RestTemplate().exchange("http://localhost:8081/api/v1/animes",
                HttpMethod.POST,
                new HttpEntity<>(anime3),
                Anime.class);
        log.info("saved anime {}", savedAnime3);

        Anime anime4 = savedAnime3.getBody();
        anime4.setName("One Punchman Atualizado");
        ResponseEntity<Void> updatedAnime4 = new RestTemplate().exchange("http://localhost:8081/api/v1/animes",
                HttpMethod.PUT,
                new HttpEntity<>(anime4),
                Void.class);
        log.info("updated anime {}", updatedAnime4);

        ResponseEntity<Void> deleteddAnime4 = new RestTemplate().exchange("http://localhost:8081/api/v1/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                anime4.getId());
        log.info("deleted anime {}", deleteddAnime4);
    }
}
