package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {

    private String title;
    private String body;
}
