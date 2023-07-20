package com.calapzu.redditclone.mapper;

import com.calapzu.redditclone.dto.SubRedditDto;
import com.calapzu.redditclone.model.Post;
import com.calapzu.redditclone.model.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subReddit.getPosts()))")
    SubRedditDto mapSubRedditToDto(SubReddit subReddit);

    default Integer mapPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubReddit mapDtoToSubReddit(SubRedditDto subRedditDto);
}
