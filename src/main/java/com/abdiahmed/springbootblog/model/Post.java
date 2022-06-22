package com.abdiahmed.springbootblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String title;
  private String body;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Comment> comments = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @JsonIgnore
  private User user;

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setPost(this);
  }

  public void deleteComment(Comment comment) {
    comments.remove(comment);
    comment.setPost(null);
  }
}
