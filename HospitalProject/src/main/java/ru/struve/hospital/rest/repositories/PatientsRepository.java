package ru.struve.hospital.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.struve.hospital.rest.models.Patient;


import java.util.Date;
import java.util.Optional;

@Repository
public interface PatientsRepository extends JpaRepository<Patient, Integer> {
        Optional<Patient> findById(int id);
        Optional<Patient> findByFullNameAndDateOfBirth(String fullName, Date dateOfBirth);
        Optional<Patient>findByFullName(String fullName);

}
