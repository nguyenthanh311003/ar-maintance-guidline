package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.CompanySubscription.ComSubscriptionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.OrderTransaction.OrderTransactionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.OrderTransaction.OrderTransactionResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.OrderTransaction;
import com.capstone.ar_guideline.entities.Subscription;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.OrderTransactionMapper;
import com.capstone.ar_guideline.repositories.OrderTransactionRepository;
import com.capstone.ar_guideline.services.ICompanySubscriptionService;
import com.capstone.ar_guideline.services.IOrderTransactionService;
import com.capstone.ar_guideline.services.ISubscriptionService;
import com.capstone.ar_guideline.services.IUserService;
import com.capstone.ar_guideline.util.UtilService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderTransactionServiceImpl implements IOrderTransactionService {
  OrderTransactionRepository orderTransactionRepository;
  RedisTemplate<String, Object> redisTemplate;
  IUserService userService;
  ISubscriptionService subscriptionService;
  ICompanySubscriptionService companySubscriptionService;

  private final String[] keysToRemove = {ConstHashKey.HASH_KEY_ORDER_TRANSACTION};

  @Override
  @Transactional
  public OrderTransactionResponse create(OrderTransactionCreationRequest request) {
    try {
      User userById = userService.findById(request.getUserId());

    Subscription subscription = subscriptionService.findByCode(request.getItemCode());

      OrderTransaction newOrderTransaction =
          OrderTransactionMapper.fromOrderTransactionCreationRequestToEntity(request, userById);
      newOrderTransaction.setStatus(ConstStatus.PENDING);

      String orderCodeRandom = UUID.randomUUID().toString().replace("-", "");

      newOrderTransaction.setAmount(subscription.getMonthlyFee());

      newOrderTransaction = orderTransactionRepository.save(newOrderTransaction);
      ComSubscriptionCreationRequest comSubscriptionCreationRequest =
          ComSubscriptionCreationRequest.builder()
              .companyId(userById.getCompany().getId())
              .subscriptionId(subscription.getId())
              .build();

      companySubscriptionService.create(comSubscriptionCreationRequest);

      return OrderTransactionMapper.fromEntityToOrderTransactionResponse(newOrderTransaction);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ORDER_TRANSACTION_CREATE_FAILED);
    }
  }

  @Override
  public OrderTransactionResponse update(String id, OrderTransactionCreationRequest request) {
    try {
      OrderTransaction orderTransactionById = findById(id);

      User userById = userService.findById(request.getUserId());

      orderTransactionById.setUser(userById);
      orderTransactionById.setItemCode(request.getItemCode());

      orderTransactionById = orderTransactionRepository.save(orderTransactionById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return OrderTransactionMapper.fromEntityToOrderTransactionResponse(orderTransactionById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ORDER_TRANSACTION_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      OrderTransaction orderTransactionById = findById(id);

      orderTransactionRepository.deleteById(orderTransactionById.getId());

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ORDER_TRANSACTION_DELETE_FAILED);
    }
  }

  @Override
  public OrderTransaction findById(String id) {
    try {
      return orderTransactionRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.ORDER_TRANSACTION_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ORDER_TRANSACTION_NOT_EXISTED);
    }
  }

  @Override
  public void UpdateOrderCode(String orderId, Long orderCode) {
    OrderTransaction orderTransaction = findById(orderId);
    orderTransaction.setOrderCode(orderCode);
    orderTransactionRepository.save(orderTransaction);

  }

  @Override
  public PagingModel<OrderTransactionResponse> getAllTransactionByCompanyId(
      int page, int size, String companyId) {
    try {
      PagingModel<OrderTransactionResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      Page<OrderTransaction> orderTransactions =
          orderTransactionRepository.getOrderTransactionByCompanyId(pageable, companyId);

      List<OrderTransactionResponse> orderTransactionResponses =
          orderTransactions.getContent().stream()
              .map(OrderTransactionMapper::fromEntityToOrderTransactionResponse)
              .toList();

      pagingModel.setPage(page);
      pagingModel.setSize(size);
      pagingModel.setTotalItems((int) orderTransactions.getTotalElements());
      pagingModel.setTotalPages(orderTransactions.getTotalPages());
      pagingModel.setObjectList(orderTransactionResponses);
      return pagingModel;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ORDER_TRANSACTION_NOT_EXISTED);
    }
  }

  @Override
  public OrderTransaction findByOrderCode(Long orderCode) {
    try {
      return orderTransactionRepository.findByOrderCode(orderCode);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.ORDER_TRANSACTION_NOT_EXISTED);
    }
  }

  @Override
  public void changeStatus(String orderId, String status) {
    OrderTransaction orderTransaction = findById(orderId);
    orderTransaction.setStatus(status);
    orderTransactionRepository.save(orderTransaction);
  }
}
