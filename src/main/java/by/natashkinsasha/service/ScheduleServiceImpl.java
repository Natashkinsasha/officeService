package by.natashkinsasha.service;


import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.model.DaySchedule;
import by.natashkinsasha.model.Reservations;
import by.natashkinsasha.model.comparator.ComparatorDayScheduleByData;
import by.natashkinsasha.model.comparator.ComparatorReservationByTime;
import by.natashkinsasha.repository.BookingRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookingRequestRepository bookingRequestRepository;


    @Override
    public List<DaySchedule> create(Long startData, Long finishData, Long startWorkTime, Long finishWorkTime) {
        List<BookingRequest> bookingRequests = bookingRequestRepository.findByStartSubmissionDataBetweenAndStartSubmissionTimeGreaterThanAndFinishSubmissionTimeLessThan(startData, finishData, startWorkTime-1, finishWorkTime+1);
        List<DaySchedule> daySchedules = shapeSchedules(bookingRequests);
        return daySchedules;
    }



    private List<DaySchedule> shapeSchedules(List<BookingRequest> bookingRequestList) {
        Map<Long, List<BookingRequest>> groupBySubmissionData = bookingRequestList.parallelStream().collect(Collectors.groupingBy((bookingRequest) -> bookingRequest.getStartSubmissionData()));
        List<DaySchedule> daySchedules = groupBySubmissionData.entrySet().parallelStream().map(entry -> {
            DaySchedule daySchedule = new DaySchedule();
            daySchedule.setDate(entry.getKey());
            List<Reservations> reservationsList = entry.getValue().parallelStream().map(bookingRequest -> {
                Reservations reservations = new Reservations();
                reservations.setUserId(bookingRequest.getUserId());
                reservations.setStartDuration(bookingRequest.getStartSubmissionTime());
                reservations.setFinishDuration(bookingRequest.getFinishSubmissionTime());
                return reservations;
            }).sorted(new ComparatorReservationByTime()).collect(Collectors.toList());
            daySchedule.setReservations(reservationsList);
            return daySchedule;
        }).sorted(new ComparatorDayScheduleByData()).collect(Collectors.toList());
        return daySchedules;
    }


}
