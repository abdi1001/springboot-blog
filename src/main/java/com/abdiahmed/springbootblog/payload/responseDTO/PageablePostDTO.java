package com.abdiahmed.springbootblog.payload.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageablePostDTO {
    private List<PostResponseDTO> responseDTOList;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
    private boolean isLast;
}
