package com.apps.redditclonebackend.controller;


import com.apps.redditclonebackend.dto.SubredditDto;
import com.apps.redditclonebackend.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // mean that this class is a controller and it will return json response
/*
Rest Controller is a type of controller that returns JSON data.
 */
@AllArgsConstructor // it will inject all the dependencies
@Slf4j // mean that it will log all the logs in the console
/*
the logs of Slf4j are :

- debug = log.debug("message");
- info = log.info("message");
- warn = log.warn("message");
- error = log.error("message");
- fatal = log.fatal("message");
 */


@RequestMapping("/api/subreddit")
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping("/create")
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity.ok(subredditService.saveSubreddit(subredditDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity.ok(subredditService.getAllSubreddits());
    }
}
