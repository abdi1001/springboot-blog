package com.abdiahmed.springbootblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String comment;
    @ManyToOne()
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    @JsonIgnore
    private Post post;
}
