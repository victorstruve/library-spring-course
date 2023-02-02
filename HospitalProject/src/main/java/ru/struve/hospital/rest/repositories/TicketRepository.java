package ru.struve.hospital.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.struve.hospital.rest.models.Ticket;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    void deleteAllByDateBetween(ZonedDateTime dateAfter, ZonedDateTime dateBefore);
    List<Ticket> findAllByDateBetween(ZonedDateTime startWork, ZonedDateTime endWork);
    List<Ticket> findAllByDoctorUuid(UUID doctorUUID);
    List<Ticket> findAllByPatientUuid(UUID patientUUID);


}
