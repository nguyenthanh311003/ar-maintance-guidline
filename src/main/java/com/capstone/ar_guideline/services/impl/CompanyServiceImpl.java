package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.Company.CompanyCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import com.capstone.ar_guideline.entities.Company;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CompanyMapper;
import com.capstone.ar_guideline.repositories.CompanyRepository;
import com.capstone.ar_guideline.services.ICompanyService;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CompanyServiceImpl implements ICompanyService {
  CompanyRepository companyRepository;
  RedisTemplate<String, Object> redisTemplate;

  @Override
  public List<CompanyResponse> findAll() {
    try {
      List<Company> companies = companyRepository.findAll();
      return companies.stream().map(CompanyMapper::fromEntityToCompanyResponse).toList();
    } catch (Exception exception) {
      log.error("Company find all failed: {}", exception.getMessage());
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_FIND_ALL_FAILED);
    }
  }

  @Override
  public Company create(CompanyCreationRequest request) {
    try {
      Company newCompany = CompanyMapper.fromCompanyCreationRequestToEntity(request);
      return companyRepository.save(newCompany);
    } catch (Exception exception) {
      log.error("Company create failed: {}", exception.getMessage());
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_CREATE_FAILED);
    }
  }

  @Override
  public CompanyResponse update(String id, CompanyCreationRequest request) {
    Company companyByIdWithRedis =
        (Company) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_COMPANY, id);

    if (!Objects.isNull(companyByIdWithRedis)) {
      companyByIdWithRedis.setCompanyName(request.getCompanyName());
      redisTemplate.opsForHash().put(ConstHashKey.HASH_KEY_COMPANY, id, companyByIdWithRedis);
      companyRepository.save(companyByIdWithRedis);
      return CompanyMapper.fromEntityToCompanyResponse(companyByIdWithRedis);
    }

    Company companyById = CompanyMapper.fromCompanyResponseToEntity(findById(id));

    companyById.setCompanyName(request.getCompanyName());
    companyById = companyRepository.save(companyById);
    return CompanyMapper.fromEntityToCompanyResponse(companyById);
  }

  @Override
  public void delete(String id) {
    Company companyById = CompanyMapper.fromCompanyResponseToEntity(findById(id));
    redisTemplate.opsForHash().delete(ConstHashKey.HASH_KEY_COMPANY, id);
    companyRepository.deleteById(companyById.getId());
  }

  @Override
  public CompanyResponse findById(String id) {
    Company companyByIdWithRedis =
        (Company) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_COMPANY, id);

    if (!Objects.isNull(companyByIdWithRedis)) {
      return CompanyMapper.fromEntityToCompanyResponse(companyByIdWithRedis);
    }

    Company companyById =
        companyRepository
            .findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXISTED));
    return CompanyMapper.fromEntityToCompanyResponse(companyById);
  }

  @Override
  public CompanyResponse findByName(String name) {
    Company companyByNameWithRedis =
        (Company) redisTemplate.opsForHash().get(ConstHashKey.HASH_KEY_COMPANY, name);

    if (!Objects.isNull(companyByNameWithRedis)) {
      return CompanyMapper.fromEntityToCompanyResponse(companyByNameWithRedis);
    }

    Company companyByName =
        companyRepository
            .findByCompanyName(name)
            .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXISTED));
    return CompanyMapper.fromEntityToCompanyResponse(companyByName);
  }

  @Override
  public Company findCompanyEntityByName(String name) {
    Company companyByName =
        companyRepository
            .findByCompanyName(name)
            .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXISTED));
    return companyByName;
  }

  @Override
  public Company findByIdReturnEntity(String id) {
    try {
      return companyRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COMPANY_NOT_EXISTED);
    }
  }
}
