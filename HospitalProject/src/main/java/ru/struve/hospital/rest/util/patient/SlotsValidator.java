package ru.struve.hospital.rest.util.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.struve.hospital.rest.dto.SlotsResponse;
import ru.struve.hospital.rest.services.PatientsService;

@Component
public class SlotsValidator implements Validator {
    private final PatientsService patientsService;

    @Autowired
    public SlotsValidator(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SlotsResponse.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SlotsResponse slotsResponse = (SlotsResponse) target;
        if(patientsService.findAll().stream().filter(x->x.getId()==slotsResponse.getPatientId()).toList().isEmpty()){
            errors.rejectValue("patientId","Пациента с таким id не существует");
        }
    }
}
