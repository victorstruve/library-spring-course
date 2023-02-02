package ru.struve.hospital.rest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.struve.hospital.rest.dto.DoctorDTO;
import ru.struve.hospital.rest.models.Doctor;
import ru.struve.hospital.rest.services.DoctorsService;
import ru.struve.hospital.rest.util.ClientException;
import ru.struve.hospital.rest.util.doctor.DoctorErrorResponse;
import ru.struve.hospital.rest.util.doctor.DoctorValidator;


import java.util.List;
import java.util.stream.Collectors;

import static ru.struve.hospital.rest.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {
    private final DoctorsService doctorsService;
    private final ModelMapper modelMapper;
    private final DoctorValidator doctorValidator;

    @Autowired
    public DoctorsController(DoctorsService doctorsService, ModelMapper modelMapper,
                             DoctorValidator doctorValidator) {
        this.doctorsService = doctorsService;
        this.modelMapper = modelMapper;
        this.doctorValidator = doctorValidator;
    }

    @GetMapping()
    public List<DoctorDTO> getDoctors(){
        return doctorsService.findAll().stream().map(this::convertToDoctorDTO).collect(Collectors.toList());
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid DoctorDTO doctorDTO,
                                          BindingResult bindingResult){
        Doctor doctor= convertToDoctor(doctorDTO);
        doctorValidator.validate(doctor, bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        doctorsService.save(doctor);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<DoctorErrorResponse> handleException(ClientException err){
        DoctorErrorResponse response = new DoctorErrorResponse(
                err.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    public DoctorDTO convertToDoctorDTO(Doctor doctor){
        return modelMapper.map(doctor, DoctorDTO.class);
    }
    public Doctor convertToDoctor(DoctorDTO doctorDTO){
        return modelMapper.map(doctorDTO, Doctor.class);
    }


}
