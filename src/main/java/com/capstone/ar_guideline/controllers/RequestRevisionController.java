package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionRequest;
import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionResponse;
import com.capstone.ar_guideline.services.impl.RequestRevisionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/request-revisions")
@RequiredArgsConstructor
public class RequestRevisionController {

  private final RequestRevisionService requestRevisionService;

  @PostMapping
  public ResponseEntity<RequestRevisionResponse> createRequestRevision(
      @RequestBody RequestRevisionRequest request) {
    RequestRevisionResponse response = requestRevisionService.create(request);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RequestRevisionResponse> updateRequestRevision(
      @PathVariable String id, @ModelAttribute RequestRevisionRequest request) {
    RequestRevisionResponse response = requestRevisionService.update(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRequestRevision(@PathVariable String id) {
    requestRevisionService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/company-request/{companyRequestId}")
  public ResponseEntity<List<RequestRevisionResponse>> getAllByCompanyRequestId(
      @PathVariable String companyRequestId) {
    List<RequestRevisionResponse> responses =
        requestRevisionService.getAllByCompanyRequestId(companyRequestId);
    return ResponseEntity.ok(responses);
  }
}
