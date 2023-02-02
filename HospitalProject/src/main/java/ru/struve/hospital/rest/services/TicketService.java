package ru.struve.hospital.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.struve.hospital.rest.dto.DoctorSearchForm;
import ru.struve.hospital.rest.dto.RecordFormDTO;
import ru.struve.hospital.rest.models.Ticket;
import ru.struve.hospital.rest.repositories.TicketRepository;

import java.sql.Time;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class TicketService {
    private final TicketRepository ticketRepository;
    private final DoctorsService doctorsService;
    private final PatientsService patientsService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, DoctorsService doctorsService,
                         PatientsService patientsService) {
        this.ticketRepository = ticketRepository;
        this.doctorsService = doctorsService;
        this.patientsService = patientsService;
    }

    public Map<Integer,LocalTime> checkDoctor(DoctorSearchForm doctorSearchForm){
        ZonedDateTime startWork = dateConvertor(doctorSearchForm.getYear(), doctorSearchForm.getMonth()-1, doctorSearchForm.getDay());
        return converterToMap(doctorSearchForm, startWork);
    }

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }
    public Ticket findById(int id){
        return ticketRepository.findById(id).orElse(null);
    }
    public ZonedDateTime dateConvertor(int year, int month, int day){
        return new GregorianCalendar(year, month, day).toZonedDateTime();
    }

    public Map<Integer, LocalTime> converterToMap(DoctorSearchForm doctorSearchForm, ZonedDateTime startWork){
        Map<Integer,LocalTime> result = new TreeMap<>();
        List<ZonedDateTime> time = new ArrayList<>();
        ticketRepository.findAllByDoctorUuid(doctorsService.findOne(doctorSearchForm.getDoctorId()).getUuid()).forEach(x-> time.add(x.getDate()));
        ticketRepository.findAllByDateBetween(startWork,startWork.plusDays(1)).stream()
                .filter(x->!time.contains(x.getDate()))
                .forEach(x->result.put(x.getId(),new Time(
                        x.getDate().getHour(), x.getDate().getMinute(), x.getDate().getSecond())
                        .toLocalTime()));
        return result;
    }
    public List<String> getDataByUUID(UUID patientUUID){
        List<String> result = new ArrayList<>();
        ticketRepository.findAllByPatientUuid(patientUUID)
                .forEach(x->result.add(convertDataToString(x.getDate())+" id врача: "+x.getDoctor().getId()));
        Collections.sort(result);
        return result;
    }
    public String convertDataToString(ZonedDateTime date){
        return date.toLocalDate()+" "+date.toLocalTime();
    }

    @Transactional
    public void addRecord(RecordFormDTO recordFormDTO, int patientId){
        Ticket ticket = findById(recordFormDTO.getRecordId());
        ticket.setDoctor(doctorsService.findOne(recordFormDTO.getDoctorId()));
        ticket.setPatient(patientsService.findById(patientId).get());
        doctorsService.findOne(recordFormDTO.getDoctorId()).getTickets().add(ticket);
        patientsService.findById(patientId).get().getTickets().add(ticket);
        ticketRepository.save(ticket);
    }
    @Transactional
    public void delete(ZonedDateTime date){
        ZonedDateTime dateToDelete = new GregorianCalendar(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth()).toZonedDateTime();
        ticketRepository.deleteAllByDateBetween(dateToDelete, dateToDelete.plusDays(1));
    }
}
