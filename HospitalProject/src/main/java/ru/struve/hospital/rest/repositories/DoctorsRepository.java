package ru.struve.hospital.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.struve.hospital.rest.models.Doctor;

import java.util.Optional;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByFullName(String name);
}
