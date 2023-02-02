package ru.struve.hospital.rest.util.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.struve.hospital.rest.models.Patient;
import ru.struve.hospital.rest.services.PatientsService;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class PatientValidator implements Validator {
    private final PatientsService patientsService;

    @Autowired
    public PatientValidator(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Patient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Patient patient = (Patient) target;
        if(patientsService.findByFullNameAndDateOfBirth(patient.getFullName(), patient.getDateOfBirth()).isPresent())
            errors.rejectValue("fullName","Пациент с такими данными уже существует!");
        if(patientsService.findByFullName(patient.getFullName()).isPresent())
            errors.rejectValue("fullName","Пациент с такими данными уже существует!");
        }
}

