package com.selectit.akamaidemo.socialnetworkpost.controller;

import com.selectit.akamaidemo.common.exceptions.ResourceNotFoundExc;
import com.selectit.akamaidemo.socialnetworkpost.dao.SocialNetworkPostDAO;
import com.selectit.akamaidemo.socialnetworkpost.model.SocialNetworkPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.selectit.akamaidemo.common.StringUtils.POST_NOT_FOUND;

@RestController
@RequestMapping("/api/socialnetwork/posts")
public class SocialNetworkPostController {
  final SocialNetworkPostDAO socialNetworkPostDAO;

  public SocialNetworkPostController(SocialNetworkPostDAO socialNetworkPostDAO) {
    this.socialNetworkPostDAO = socialNetworkPostDAO;
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<SocialNetworkPost> getPost(@PathVariable Long id) throws ResourceNotFoundExc {
    final SocialNetworkPost post = socialNetworkPostDAO.findById(id).orElseThrow(() -> new ResourceNotFoundExc(POST_NOT_FOUND));
    post.incrementViewCount();
    socialNetworkPostDAO.save(post);
    return ResponseEntity.ok(post);
  }

  @GetMapping(value = "/top10")
  public ResponseEntity<List<SocialNetworkPost>> findTop10Posts() {
    List<SocialNetworkPost> top10Posts = socialNetworkPostDAO.findTop10PostsByViewCount();

    return new ResponseEntity<>(top10Posts, HttpStatus.OK);
  }

  @PostMapping
  SocialNetworkPost createPost(@RequestBody SocialNetworkPost newPost) {
    newPost.setPostDate(LocalDateTime.now());
    newPost.setViewCount(1L);
    return socialNetworkPostDAO.save(newPost);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<?> deletePost(@PathVariable Long id) throws ResourceNotFoundExc {
    final SocialNetworkPost postToBeDeleted = socialNetworkPostDAO.findById(id).orElseThrow(() -> new ResourceNotFoundExc(POST_NOT_FOUND));
    socialNetworkPostDAO.delete(postToBeDeleted);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<SocialNetworkPost> editPost(@PathVariable Long id, @RequestBody SocialNetworkPost snp) {
    return socialNetworkPostDAO.findById(id).map(existingPost -> {
      existingPost.setAuthor(snp.getAuthor());
      existingPost.setContent(snp.getContent());

      final SocialNetworkPost updatedPost = socialNetworkPostDAO.save(existingPost);
      return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }).orElseGet(() -> {
      SocialNetworkPost newPost = SocialNetworkPost.builder()
          .postDate(LocalDateTime.now())
          .author(snp.getAuthor())
          .content(snp.getContent())
          .viewCount(1L).build();
      final SocialNetworkPost savedPost = socialNetworkPostDAO.save(newPost);
      return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    });
  }
}
