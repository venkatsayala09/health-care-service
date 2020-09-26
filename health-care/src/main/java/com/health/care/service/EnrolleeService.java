package com.health.care.service;

import com.health.care.dto.EnrolleeDTO;
import com.health.care.entity.Dependent;
import com.health.care.entity.Enrollee;
import com.health.care.exception.ResourceNotFoundException;
import com.health.care.repository.EnrolleeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@Service
public class EnrolleeService {

    @Autowired
    private EnrolleeRepository repository;


    public List<EnrolleeDTO> getAllEnrollee() {
        ModelMapper modelMapper = new ModelMapper();
        List<Enrollee> enrollees = repository.findAll();
        Type listType = new TypeToken<List<EnrolleeDTO>>() {
        }.getType();
        List<EnrolleeDTO> enrolleeDTOs = modelMapper.map(enrollees, listType);
        return enrolleeDTOs;
    }

    public EnrolleeDTO getEnrolleeInformation(long enrolleeId) {
        ModelMapper modelMapper = new ModelMapper();
        Enrollee enrollee = repository.findById(enrolleeId)
                .orElseThrow(() -> new ResourceNotFoundException("EnrolleeId : " + enrolleeId + " not found"));
        return modelMapper.map(enrollee, EnrolleeDTO.class);
    }

    public EnrolleeDTO saveEnrolleeInformation(EnrolleeDTO enrolleeDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Enrollee enrollee = modelMapper.map(enrolleeDTO, Enrollee.class);
        enrollee = repository.saveAndFlush(enrollee);
        EnrolleeDTO updatedEnrollee = modelMapper.map(enrollee, EnrolleeDTO.class);
        return updatedEnrollee;
    }

    public EnrolleeDTO updateEnrolleeInformation(EnrolleeDTO enrolleeDTO, long enrolleeId) {
        ModelMapper modelMapper = new ModelMapper();
        repository.findById(enrolleeId)
                .orElseThrow(() -> new ResourceNotFoundException("EnrolleeId : " + enrolleeId + " not found"));
        Enrollee enrollee = modelMapper.map(enrolleeDTO, Enrollee.class);
        enrollee = repository.saveAndFlush(enrollee);
        EnrolleeDTO updatedEnrollee = modelMapper.map(enrollee, EnrolleeDTO.class);
        return updatedEnrollee;
    }

    public void deleteEnrolleeInformation(long enrolleeId) {
        repository.deleteById(enrolleeId);
    }

}
