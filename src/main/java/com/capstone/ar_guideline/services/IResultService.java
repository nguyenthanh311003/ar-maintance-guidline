package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Result.ResultCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Result.ResultResponse;
import com.capstone.ar_guideline.entities.Result;

public interface IResultService {
  ResultResponse create(ResultCreationRequest request);

  ResultResponse update(String id, ResultCreationRequest request);

  void delete(String id);

  Result findById(String id);
}
