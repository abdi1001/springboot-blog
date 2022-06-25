package com.abdiahmed.springbootblog.payload.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableUserDTO {
  private Set<UserResponseDTO> responseDTOList;
  private int pageNo;
  private int pageSize;
  private int totalPages;
  private Long totalElements;
  private boolean isLast;
}
