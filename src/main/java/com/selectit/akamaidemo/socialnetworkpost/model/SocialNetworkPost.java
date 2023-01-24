package com.selectit.akamaidemo.socialnetworkpost.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "socialnetworkpost")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SocialNetworkPost {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  LocalDateTime postDate;
  String author;
  String content;
  Long viewCount;

  public void incrementViewCount() {
    setViewCount(viewCount + 1L);
  }
}