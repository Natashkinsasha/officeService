package by.natashkinsasha.service;


import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.model.DaySchedule;
import by.natashkinsasha.model.Reservations;
import by.natashkinsasha.model.comparator.ComparatorBookingRequestByBookingDate;
import by.natashkinsasha.model.comparator.ComparatorDayScheduleByData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<DaySchedule> create(LocalTime startWorktime, LocalTime finishWorkTime, List<BookingRequest> bookingRequests) {
        logger.debug("Start work time:{}, finish work time:{}, BookingRequests:{}", startWorktime, finishWorkTime, bookingRequests);
        List<BookingRequest> bookingRequestList = removeOutWork(bookingRequests, startWorktime, finishWorkTime);
        bookingRequestList = removeOverlapping(bookingRequestList);
        List<DaySchedule> daySchedules = shapeSchedules(bookingRequestList);
        return daySchedules;
    }


    private List<BookingRequest> removeOverlapping(List<BookingRequest> bookingRequestList) {
        bookingRequestList.sort(new ComparatorBookingRequestByBookingDate());
        int size = bookingRequestList.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (bookingRequestList.get(j).isOverlapping(bookingRequestList.get(i))) {
                    bookingRequestList.remove(j);
                    j--;
                    size--;
                }
            }
        }
        return bookingRequestList;
    }

    private List<BookingRequest> removeOutWork(List<BookingRequest> bookingRequests, LocalTime startWorktime, LocalTime finishWorkTime) {
        return bookingRequests.parallelStream().filter(bookingRequest ->
                ((!bookingRequest.getStartSubmissionTime().toLocalTime().isBefore(startWorktime)) && (!bookingRequest.getFinishSubmissionTime().toLocalTime().isAfter(finishWorkTime))))
                .collect(Collectors.toList());
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
            }).collect(Collectors.toList());
            daySchedule.setReservations(reservationsList);
            return daySchedule;
        }).sorted(new ComparatorDayScheduleByData()).collect(Collectors.toList());
        return daySchedules;
    }
}
