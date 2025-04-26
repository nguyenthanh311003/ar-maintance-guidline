package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionRequest;
import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionResponse;
import com.capstone.ar_guideline.dtos.responses.ChatMessage.ChatMessageResponse;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.repositories.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestRevisionService {

  private final RequestRevisionRepository requestRevisionRepository;
  private final CompanyRequestRepository companyRequestRepository;
  private final UserRepository userRepository;
  private final ModelServiceImpl modelService;
  private final ModelRepository modelRepository;
  private final WalletServiceImpl walletService;
  private final ServicePricerRepository servicePricerRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @Transactional
  public RequestRevisionResponse create(RequestRevisionRequest request) {

    try {
      CompanyRequest companyRequest =
          companyRequestRepository.findByRequestId(request.getCompanyRequestId());

      Optional<ChatMessage> chatMessage = chatMessageRepository.findById(UUID.fromString(request.getChatMessageId()));

      RequestRevision requestRevision = new RequestRevision();
      requestRevision.setCompanyRequest(companyRequest);
      requestRevision.setReason(request.getReason());
      requestRevision.setChatMessage(chatMessage.get());
      requestRevision.setStatus(ConstStatus.PENDING);
      requestRevision.setType(request.getType());


      requestRevision = requestRevisionRepository.save(requestRevision);
      final RequestRevision savedRequestRevision = requestRevision;
      List<RevisionFile> revisionFiles =
          request.getRevisionFiles().stream()
              .map(
                  file -> {
                    RevisionFile revisionFile = new RevisionFile();
                    revisionFile.setRequestRevision(savedRequestRevision);
                    revisionFile.setFileName(FileStorageService.storeFile(file));
                    return revisionFile;
                  })
              .collect(Collectors.toList());

      requestRevision.setRevisionFiles(revisionFiles);
      requestRevision = requestRevisionRepository.save(requestRevision);

      ChatMessageResponse response =
              ChatMessageResponse.builder()
                      .id(String.valueOf(chatMessage.get().getId()))
                      .content(chatMessage.get().getContent())
                      .senderEmail(chatMessage.get().getUser().getEmail())
                      .timestamp(chatMessage.get().getTimestamp().toString())
                      .requestRevisionResponse(mapToResponse(requestRevision))
                      .build();
      String chatId = requestRevision.getCompanyRequest().getRequestId();
      simpMessagingTemplate.convertAndSend("/topic/chat/" + chatId, response);


      return mapToResponse(requestRevision);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid price proposal format");
    }
  }

  @Transactional(readOnly = true)
  public List<RequestRevisionResponse> getAllByCompanyRequestId(String companyRequestId) {
    List<RequestRevision> revisions =
        requestRevisionRepository.findAllByCompanyRequestRequestId(companyRequestId);
    return revisions.stream().map(this::mapToResponse).collect(Collectors.toList());
  }

  @Transactional
  public RequestRevisionResponse update(String id, RequestRevisionRequest request) {
    RequestRevision requestRevision =
        requestRevisionRepository
            .findById(UUID.fromString(id))
            .orElseThrow(() -> new RuntimeException("RequestRevision not found"));

    switch (request.getStatus()) {
      case ConstStatus.PRICE_PROPOSED:
        requestRevision.setStatus(request.getStatus());
        requestRevision.setPriceProposal(request.getPriceProposal());
        break;
      case ConstStatus.REJECTED:
        User user = userRepository.findUserById(request.getRejectionReason());
        requestRevision.setRejectionReason(request.getRejectionReason());
        requestRevision.setUserReject(user);
        requestRevision.setStatus(request.getStatus());
        break;
      case ConstStatus.DELIVERED:
        requestRevision.setStatus(request.getStatus());
        requestRevision.setModelFile(FileStorageService.storeFile(request.getModelFile()));
        break;

      case ConstStatus.APPROVED:
        requestRevision.setStatus(request.getStatus());
        Model model = new Model();
        model.setFile(requestRevision.getModelFile());
        model.setStatus(ConstStatus.ACTIVE_STATUS);
        model.setModelType(
            ModelType.builder()
                .id(requestRevision.getCompanyRequest().getMachineType().getId())
                .build());
        model.setName(request.getModelName());
        model.setDescription(request.getDescription());
        model.setCompany(requestRevision.getCompanyRequest().getCompany());
        model.setPosition("0,0,0");
        model.setRotation("0,0,0");
        model.setScale("1");

        modelRepository.save(model);
        CompanyRequest companyRequest =
            companyRequestRepository.findByRequestId(
                requestRevision.getCompanyRequest().getRequestId());
        companyRequest.setStatus(ConstStatus.APPROVED);
        companyRequest.setAssetModel(model);
        companyRequestRepository.save(companyRequest);
        break;
      case ConstStatus.PROCESSING:

        if(!requestRevision.getType().equals("Bug Fix"))
        {
          User company =
                  userRepository.findUserByCompanyIdAndAdminRole(
                          requestRevision.getCompanyRequest().getCompany().getId());
          ServicePrice servicePrice = servicePricerRepository.findByName("Model Request");

          walletService.updateBalanceForRequestRevision(
                  company.getWallet().getId(), requestRevision.getId().toString());
          requestRevision.setStatus(request.getStatus());
          break;
        }else{
          requestRevision.setStatus(request.getStatus());
          break;
        }

    }

    requestRevision = requestRevisionRepository.save(requestRevision);
    String chatId = requestRevision.getCompanyRequest().getRequestId();

    ChatMessageResponse response =
        ChatMessageResponse.builder()
            .id(String.valueOf(requestRevision.getChatMessage().getId()))
            .content(requestRevision.getChatMessage().getContent())
            .senderEmail(requestRevision.getChatMessage().getUser().getEmail())
            .timestamp(requestRevision.getChatMessage().getTimestamp().toString())
            .requestRevisionResponse(mapToResponse(requestRevision))
            .build();

    simpMessagingTemplate.convertAndSend("/topic/chat/" + chatId, response);

    return mapToResponse(requestRevision);
  }

  @Transactional
  public void delete(String id) {
    RequestRevision requestRevision =
        requestRevisionRepository
            .findById(UUID.fromString(id))
            .orElseThrow(() -> new RuntimeException("RequestRevision not found"));

    requestRevisionRepository.delete(requestRevision);
  }

  public RequestRevisionResponse mapToResponse(RequestRevision requestRevision) {
    return RequestRevisionResponse.builder()
        .id(requestRevision.getId().toString())
        .companyRequestId(requestRevision.getCompanyRequest().getRequestId())
        .reason(requestRevision.getReason())
        .rejectionReason(requestRevision.getRejectionReason())
        .priceProposal(requestRevision.getPriceProposal())
            .type(requestRevision.getType())
        .status(requestRevision.getStatus())
            .modelFile(requestRevision.getModelFile())
        .revisionFiles(
            requestRevision.getRevisionFiles().stream()
                .map(RevisionFile::getFileName)
                .collect(Collectors.toList()))
        .createdDate(requestRevision.getCreatedDate())
            .chatBoxId(requestRevision.getChatMessage().getChatBox().getId().toString())
        .build();
  }
}
