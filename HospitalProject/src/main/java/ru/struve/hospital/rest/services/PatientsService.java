package ru.struve.hospital.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.struve.hospital.rest.models.Patient;
import ru.struve.hospital.rest.repositories.PatientsRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PatientsService {
    private final PatientsRepository patientsRepository;

    @Autowired
    public PatientsService(PatientsRepository patientsRepository) {
        this.patientsRepository = patientsRepository;
    }

    public List<Patient> findAll(){
        return patientsRepository.findAll();
    }
    public Optional<Patient> findById(int id){
        return patientsRepository.findById(id);
    }
    public Optional<Patient> findByFullNameAndDateOfBirth(String fullName, Date dateOfBirth){
        return patientsRepository.findByFullNameAndDateOfBirth(fullName,dateOfBirth);
    }
    public Optional<Patient> findByFullName(String fullName){
        return patientsRepository.findByFullName(fullName);
    }
    @Transactional
    public void addPatient(Patient patient) {
        patientsRepository.save(patient);
    }
}
