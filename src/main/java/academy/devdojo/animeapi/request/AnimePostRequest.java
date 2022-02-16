package academy.devdojo.animeapi.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AnimePostRequest {

    @NotBlank(message = "field name can not be blank")
    private String name;
}
