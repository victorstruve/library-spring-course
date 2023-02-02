package ru.struve.hospital.rest.util.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.struve.hospital.rest.dto.DoctorSearchForm;
import ru.struve.hospital.rest.services.DoctorsService;

@Component
public class DoctorSearchFormValidation implements Validator {
    private final DoctorsService doctorsService;

    @Autowired
    public DoctorSearchFormValidation(DoctorsService doctorsService) {
        this.doctorsService = doctorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DoctorSearchForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DoctorSearchForm doctorSearchForm = (DoctorSearchForm) target;
        if (doctorsService.findAll().stream().filter(x->x.getId()==doctorSearchForm.getDoctorId()).toList().isEmpty())
            errors.rejectValue("id","Неверное id");
        if(doctorSearchForm.getYear()==0)
            errors.rejectValue("year", "Неверный год");
        if(doctorSearchForm.getMonth()==0)
            errors.rejectValue("year", "Неверный месяц");
        if(doctorSearchForm.getDay()==0)
            errors.rejectValue("year", "Неверный день");


    }
}
