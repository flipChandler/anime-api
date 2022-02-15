package academy.devdojo.animeapi.mapper;

import academy.devdojo.animeapi.domain.Anime;
import academy.devdojo.animeapi.request.AnimePostRequest;
import academy.devdojo.animeapi.request.AnimePutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {

    // gera o AnimeMapperImpl na pasta generated-sources
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequest animePostRequest);
    public abstract Anime toAnime(AnimePutRequest animePutRequest);
}
