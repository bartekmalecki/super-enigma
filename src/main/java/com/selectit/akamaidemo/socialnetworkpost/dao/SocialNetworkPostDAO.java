package com.selectit.akamaidemo.socialnetworkpost.dao;

import com.selectit.akamaidemo.socialnetworkpost.model.SocialNetworkPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SocialNetworkPostDAO extends JpaRepository<SocialNetworkPost, Long> {

  @Query(value = "SELECT * FROM SocialNetworkPost snp ORDER BY snp.view_count DESC, snp.post_date DESC LIMIT 10", nativeQuery = true)
  List<SocialNetworkPost> findTop10PostsByViewCount();

}
