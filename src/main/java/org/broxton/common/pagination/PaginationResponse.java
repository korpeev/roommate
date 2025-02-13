package org.broxton.common.pagination;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PaginationResponse<T> {
  private List<T> data;
  private PaginationMeta meta;
}
