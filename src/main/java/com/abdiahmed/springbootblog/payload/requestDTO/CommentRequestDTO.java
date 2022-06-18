package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {
//    private String name;
    private String comment;
}
