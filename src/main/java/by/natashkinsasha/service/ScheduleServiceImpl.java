package by.natashkinsasha.service;


import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.model.DaySchedule;
import by.natashkinsasha.model.Reservations;
import by.natashkinsasha.model.comparator.ComparatorBookingRequestByBookingDate;
import by.natashkinsasha.model.comparator.ComparatorDayScheduleByData;
import by.natashkinsasha.model.comparator.ComparatorReservationByTime;
import by.natashkinsasha.repository.BookingRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookingRequestRepository bookingRequestRepository;


    @Override
    public List<DaySchedule> create(LocalTime startWorkTime, LocalTime finishWorkTime, LocalDate startData, LocalDate finishData) {
        List<BookingRequest> bookingRequests = bookingRequestRepository.findAll();
        bookingRequests = removeOutWorkAndSort(bookingRequests, startWorkTime, finishWorkTime);
        List<DaySchedule> daySchedules = shapeSchedules(bookingRequests);
        return daySchedules;
    }



    private List<BookingRequest> removeOutWorkAndSort(List<BookingRequest> bookingRequests, LocalTime startWorktime, LocalTime finishWorkTime) {
        return bookingRequests.parallelStream().filter(bookingRequest ->
                ((!bookingRequest.getStartSubmissionTime().toLocalTime().isBefore(startWorktime)) && (!bookingRequest.getFinishSubmissionTime().toLocalTime().isAfter(finishWorkTime))))
                .sorted(new ComparatorBookingRequestByBookingDate()).collect(Collectors.toList());
    }






    private List<DaySchedule> shapeSchedules(List<BookingRequest> bookingRequestList) {
        Map<LocalDate, List<BookingRequest>> groupByLocalDateStartSubmissionTime = bookingRequestList.parallelStream().collect(Collectors.groupingBy((bookingRequest) -> bookingRequest.getStartSubmissionTime().toLocalDate()));
        List<DaySchedule> daySchedules = groupByLocalDateStartSubmissionTime.entrySet().parallelStream().map(entry -> {
            DaySchedule daySchedule = new DaySchedule();
            daySchedule.setDate(entry.getKey());
            List<Reservations> reservationsList = entry.getValue().parallelStream().map(bookingRequest -> {
                Reservations reservations = new Reservations();
                reservations.setUserId(bookingRequest.getUserId());
                reservations.setStartDuration(bookingRequest.getStartSubmissionTime().toLocalTime());
                reservations.setFinishDuration(bookingRequest.getFinishSubmissionTime().toLocalTime());
                return reservations;
            }).sorted(new ComparatorReservationByTime()).collect(Collectors.toList());
            daySchedule.setReservations(reservationsList);
            return daySchedule;
        }).sorted(new ComparatorDayScheduleByData()).collect(Collectors.toList());
        return daySchedules;
    }


}
