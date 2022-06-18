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
    private String title;
    private String body;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }

}
