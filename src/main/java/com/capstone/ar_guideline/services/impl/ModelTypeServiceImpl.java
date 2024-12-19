package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.ModelType.ModelTypeCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelType.ModelTypeResponse;
import com.capstone.ar_guideline.entities.ModelType;
import com.capstone.ar_guideline.mappers.ModelTypeMapper;
import com.capstone.ar_guideline.repositories.ModelTypeRepository;
import com.capstone.ar_guideline.services.IModelTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ModelTypeServiceImpl implements IModelTypeService {
    ModelTypeRepository modelTypeRepository;

    @Override
    public ModelTypeResponse create(ModelTypeCreationRequest request) {
        ModelType newModelType = ModelTypeMapper.fromModelTypeCreationRequestToEntity(request);
        newModelType = modelTypeRepository.save(newModelType);
        return ModelTypeMapper.fromEntityToModelTypeResponse(newModelType);
    }

    @Override
    public ModelTypeResponse update(String id, ModelTypeCreationRequest request) {
        ModelType modelTypeById = ModelTypeMapper.fromModelTypeResponseToEntity(findById(id));

        modelTypeById.setName(request.getName());
        modelTypeById.setImage(request.getImage());
        modelTypeById.setDescription(request.getDescription());

        modelTypeById = modelTypeRepository.save(modelTypeById);
        return ModelTypeMapper.fromEntityToModelTypeResponse(modelTypeById);
    }

    @Override
    public void delete(String id) {
        ModelType modelTypeById = ModelTypeMapper.fromModelTypeResponseToEntity(findById(id));
        modelTypeRepository.deleteById(modelTypeById.getId());
    }

    private ModelTypeResponse findById(String id) {
        ModelType modelTypeById = modelTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model Type not found !"));
        return ModelTypeMapper.fromEntityToModelTypeResponse(modelTypeById);
    }
}
