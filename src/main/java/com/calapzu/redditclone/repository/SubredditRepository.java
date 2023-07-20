package com.calapzu.redditclone.repository;

import com.calapzu.redditclone.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<SubReddit, Long> {

    Optional<SubReddit> findByName(String subRedditName);
}
