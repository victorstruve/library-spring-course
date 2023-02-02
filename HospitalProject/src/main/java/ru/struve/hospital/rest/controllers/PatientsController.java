package ru.struve.hospital.rest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.struve.hospital.rest.dto.*;
import ru.struve.hospital.rest.models.Patient;

import ru.struve.hospital.rest.services.DoctorsService;
import ru.struve.hospital.rest.services.PatientsService;
import ru.struve.hospital.rest.services.TicketService;
import ru.struve.hospital.rest.util.ClientException;
import ru.struve.hospital.rest.util.doctor.DoctorSearchFormValidation;
import ru.struve.hospital.rest.util.patient.PatientErrorResponse;
import ru.struve.hospital.rest.util.patient.PatientValidator;
import ru.struve.hospital.rest.util.patient.SlotsValidator;

import java.time.LocalTime;


import java.util.*;
import java.util.stream.Collectors;

import static ru.struve.hospital.rest.util.ErrorUtil.returnErrorsToClient;


@RestController
@RequestMapping("/patients")
public class PatientsController {
    private final PatientsService patientsService;
    private final TicketService ticketService;
    private final ModelMapper modelMapper;
    private final DoctorsService doctorsService;
    private final PatientValidator patientValidator;
    private final SlotsValidator slotsValidator;
    private final DoctorSearchFormValidation doctorSearchFormValidation;



    @Autowired
    public PatientsController(PatientsService patientsService, TicketService ticketService,
                              ModelMapper modelMapper, DoctorsService doctorsService,
                              PatientValidator patientValidator, SlotsValidator slotsValidator,
                              DoctorSearchFormValidation doctorSearchFormValidation) {
        this.patientsService = patientsService;
        this.ticketService = ticketService;
        this.modelMapper = modelMapper;
        this.doctorsService = doctorsService;
        this.patientValidator = patientValidator;
        this.slotsValidator = slotsValidator;
        this.doctorSearchFormValidation = doctorSearchFormValidation;
    }
    @GetMapping()
    public List<PatientDTO> getPatients(){
         return patientsService.findAll().stream().map(this::convertToPatientDTO).collect(Collectors.toList());

    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid PatientDTO patientDTO,
                                          BindingResult bindingResult){
        if(patientDTO.getDateOfBirth()==null)
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        Patient patient = convertToPatient(patientDTO);
        patientValidator.validate(patient,bindingResult);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        patientsService.addPatient(patient);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public PatientResponse getPatient(@PathVariable("id") int id){
        if (checkId(id))
            return new PatientResponse(new PatientDTO(), Collections.singletonList("Пациента с данным id несуществует"));
        Optional<Patient> patient = patientsService.findById(id);
        return new PatientResponse(convertToPatientDTO(patient.get()),ticketService.getDataByUUID(patient.get().getUuid()));
    }
    @PostMapping("/getSlots")
    public List<String> getSlots(@RequestBody @Valid SlotsResponse slotsResponse,
                                 BindingResult bindingResult){
        slotsValidator.validate(slotsResponse,bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        Optional<Patient> patient = patientsService.findById(slotsResponse.getPatientId());
        return ticketService.getDataByUUID(patient.get().getUuid());

    }

    @PostMapping("/checkById")
    public Map<Integer, LocalTime> selectRecord(@RequestBody @Valid DoctorSearchForm doctorSearchForm,
                                                BindingResult bindingResult){
        doctorSearchFormValidation.validate(doctorSearchForm,bindingResult);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        return ticketService.checkDoctor(doctorSearchForm);
    }

    @PutMapping("/{id}/selectDoctor")
    public String selectDoctor(@RequestBody @Valid RecordFormDTO recordFormDTO,
                               @PathVariable("id") int patientId){
        return recordFormDtoValid(recordFormDTO,patientId);
    }

    @ExceptionHandler
    private ResponseEntity<PatientErrorResponse> handleException(ClientException err){
        PatientErrorResponse response =new PatientErrorResponse(
                err.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    private boolean checkId(int id){
        return patientsService.findAll().stream().filter(x->x.getId()==id).toList().isEmpty();
    }
    private String recordFormDtoValid(RecordFormDTO recordFormDTO, int patientId){
        if(checkId(patientId))
            return "Ошибка заданного id пациента несуществует";
        if(doctorsService.findAll().stream().filter(x->x.getId()== recordFormDTO.getDoctorId()).toList().isEmpty())
            return "Ошибка неверное id врача";
        if(ticketService.findAll().stream().filter(x->x.getId()== recordFormDTO.getRecordId()).toList().isEmpty())
            return "Ошибка неверная дата";
        if(patientsService.findById(patientId).get().getTickets().contains(ticketService.findById(recordFormDTO.getRecordId())))
            return "Ошибка: Пациент уже записан к врачу на данное время";
        if(doctorsService.findOne(recordFormDTO.getDoctorId()).getTickets().contains(ticketService.findById(recordFormDTO.getRecordId())))
            return "Ошибка: Запись на данное время к врачу невозможна";
        ticketService.addRecord(recordFormDTO, patientId);
        return "Запись добавлена";
    }

    public PatientDTO convertToPatientDTO(Patient patient){
        return modelMapper.map(patient, PatientDTO.class);
    }
    public Patient convertToPatient(PatientDTO patientDTO){
        return modelMapper.map(patientDTO,Patient.class);
    }
}
