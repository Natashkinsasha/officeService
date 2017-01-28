package by.natashkinsasha.service;

import by.natashkinsasha.model.DaySchedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface ScheduleService {
    List<DaySchedule> create(Long startData, Long finishData, Long startWorkTime, Long finishWorkTime);
}
