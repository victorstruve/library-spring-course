package ru.struve.hospital.rest.util.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.struve.hospital.rest.models.Doctor;
import ru.struve.hospital.rest.services.DoctorsService;

@Component
public class DoctorValidator implements Validator {
    private final DoctorsService doctorsService;

    @Autowired
    public DoctorValidator(DoctorsService doctorsService) {
        this.doctorsService = doctorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Doctor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Doctor doctor = (Doctor) target;
        if(doctorsService.findByFullName(doctor.getFullName()).isPresent())
            errors.rejectValue("fullName","Врач с таким ФИО уже существует!");
    }
}
