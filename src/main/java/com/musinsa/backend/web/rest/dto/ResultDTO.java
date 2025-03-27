package com.musinsa.backend.web.rest.dto;

import com.musinsa.backend.domain.ResultStatus;
import java.util.Optional;

public record ResultDTO<T>(
  ResultStatus status,
  Optional<Integer> errorCode,
  Optional<String> errorMessage,
  Optional<T> result
) {

  public static <T> ResultDTO success(T result) {
    return new ResultDTO(
      ResultStatus.SUCCESS,
      Optional.empty(),
      Optional.empty(),
      Optional.of(result));
  }

  public static <T> ResultDTO failure(int errorCode, String errorMessage) {
    return new ResultDTO(
      ResultStatus.FAILURE,
      Optional.of(errorCode),
      Optional.of(errorMessage),
      Optional.empty());
  }
}
