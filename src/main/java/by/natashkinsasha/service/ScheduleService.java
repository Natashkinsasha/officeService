package by.natashkinsasha.service;

import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.model.DaySchedule;

import java.time.LocalTime;
import java.util.List;


public interface ScheduleService {
    List<DaySchedule> create(LocalTime startWorktime, LocalTime finishWorkTime, List<BookingRequest> bookingRequests);
}
