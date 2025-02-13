package org.broxton.common.pagination;

import org.springframework.data.domain.Page;

import java.util.function.Function;

public class PaginationResponseCreator {
  public static <TData, RData> PaginationResponse<RData> makePaginationResponse(Page<TData> page, Function<TData, RData> mapper) {
    PaginationMeta meta = PaginationMeta.builder()
            .size(page.getNumberOfElements())
            .pageSize(page.getSize())
            .isFirstPage(page.isFirst())
            .isLastPage(page.isLast())
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .build();

    return PaginationResponse.<RData>builder()
            .data(page.getContent().stream().map(mapper).toList())
            .meta(meta)
            .build();
  }
}
