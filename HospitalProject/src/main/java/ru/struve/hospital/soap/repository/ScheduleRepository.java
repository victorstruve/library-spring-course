package ru.struve.hospital.soap.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.struve.hospital.rest.models.Ticket;
import ru.struve.hospital.rest.repositories.TicketRepository;
import ru.struve.hospital.rest.services.TicketService;
import ru.struve.hospital.soap.model.Schedule;


import java.time.ZonedDateTime;

@Component
public class ScheduleRepository {
    private int checker =0;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    @Autowired
    public ScheduleRepository(TicketRepository ticketRepository, TicketService ticketService) {
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
    }

    public Schedule createSchedule(ZonedDateTime date, int count, int duration){
        Assert.isTrue(duration<=50 & duration>=0, "Время приёма не может быть дольше 8 часов");
        Assert.isTrue(count<=480/duration & count>=0, "Количество приёмов не может превышать время работы");
        if (checker<2){
        System.out.println(checker);
            if(!ticketRepository.findAll()
                    .stream().filter(x->x.getDate().getYear()==date.getYear())
                    .toList().isEmpty()){
                if (!ticketRepository.findAll().stream()
                        .filter(x -> x.getDate().getMonthValue() == date.getMonthValue())
                        .toList().isEmpty()) {
                    checker++;
                    Assert.isTrue(ticketRepository.findAll().stream()
                                    .filter(x -> x.getDate().getDayOfMonth() == date.getDayOfMonth())
                                    .toList().isEmpty(),
                            "Расписание на этот день уже существует, отправте запрос ещё два раза," +
                                    "если хотите перезаписать расписание на заданное");
                }
            }
        }else{
            checker=0;
            ticketService.delete(date);
        }
        Schedule schedule = new Schedule(date,count,duration);
        addData(schedule);
        System.out.println("Hours: " + date.getHour());
        return schedule;
    }
    @Transactional
    public void addData(Schedule schedule) {
        ZonedDateTime date = schedule.getDate();
        int duration = 0;
        for(int i=0; i< schedule.getDuration();i++){
            Ticket ticket = new Ticket();
            ticket.setDate(date.plusMinutes(duration));
            ticketRepository.save(ticket);
            duration+= schedule.getDuration();
        }
    }
}
