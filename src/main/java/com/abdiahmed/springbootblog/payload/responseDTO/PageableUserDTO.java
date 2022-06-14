package com.abdiahmed.springbootblog.payload.responseDTO;

import com.abdiahmed.springbootblog.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableUserDTO {
    private List<User> responseDTOList;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private Long totalElements;
    private boolean isLast;
}
