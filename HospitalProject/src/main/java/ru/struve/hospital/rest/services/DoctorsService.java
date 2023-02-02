package ru.struve.hospital.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.struve.hospital.rest.models.Doctor;
import ru.struve.hospital.rest.repositories.DoctorsRepository;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DoctorsService {
    private final DoctorsRepository doctorsRepository;

    @Autowired
    public DoctorsService(DoctorsRepository doctorsRepository) {
        this.doctorsRepository = doctorsRepository;
    }
    public List<Doctor> findAll(){
        return doctorsRepository.findAll();
    }

    public Doctor findOne(int id){
        return doctorsRepository.findById(id).orElse(null);
    }
    public Optional<Doctor> findByFullName(String fullName){
        return doctorsRepository.findByFullName(fullName);
    }


    @Transactional
    public void save(Doctor doctor){
        doctorsRepository.save(doctor);
    }
}
