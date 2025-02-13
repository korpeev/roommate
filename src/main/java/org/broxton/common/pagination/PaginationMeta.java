package org.broxton.common.pagination;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaginationMeta {
  private int totalPages;
  private long totalElements;
  private int pageSize;
  private int page;
  private int size;
  private boolean isLastPage;
  private boolean isFirstPage;
}
