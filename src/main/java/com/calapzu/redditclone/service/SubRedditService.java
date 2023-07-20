package com.calapzu.redditclone.service;

import com.calapzu.redditclone.dto.SubRedditDto;
import com.calapzu.redditclone.exceptions.SpringRedditException;
import com.calapzu.redditclone.mapper.SubRedditMapper;
import com.calapzu.redditclone.model.SubReddit;
import com.calapzu.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditService {

    private final SubredditRepository subredditRepository;
    private final SubRedditMapper subRedditMapper;

    @Transactional
    public SubRedditDto save(SubRedditDto subRedditDto) {
        SubReddit save = subredditRepository.save(subRedditMapper.mapDtoToSubReddit(subRedditDto));
        subRedditDto.setId(save.getId());
        return subRedditDto;
    }

    @Transactional(readOnly = true)
    public List<SubRedditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subRedditMapper::mapSubRedditToDto)
                .collect(toList());
    }


    public SubRedditDto getSubReddit(Long id) {
        SubReddit subReddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddi found with ID - " + id));
        return subRedditMapper.mapSubRedditToDto(subReddit);
    }
}

