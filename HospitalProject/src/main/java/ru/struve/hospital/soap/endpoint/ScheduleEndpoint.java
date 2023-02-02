package ru.struve.hospital.soap.endpoint;



import ru.struve.hospital.soap.io.spring.guides.gs_producing_web_service.GetScheduleRequest;
import ru.struve.hospital.soap.io.spring.guides.gs_producing_web_service.GetScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import ru.struve.hospital.soap.model.Schedule;
import ru.struve.hospital.soap.repository.ScheduleRepository;



@Endpoint
public class ScheduleEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleEndpoint(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getScheduleRequest")
    @ResponsePayload
    public GetScheduleResponse getSchedule(@RequestPayload GetScheduleRequest request){
        GetScheduleResponse response = new GetScheduleResponse();
        try{
            Schedule schedule = scheduleRepository.createSchedule(request.getDate(), request.getCount(), request.getDuration());
            response.setResult("Расписание создано");
            System.out.println(schedule.getDate().getHour());
            return response;
        }catch (NullPointerException n){
            response.setResult("Проверьте данные");
            return response;
        }
    }

}
