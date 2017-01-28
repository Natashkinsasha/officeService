package by.natashkinsasha.controller;

import by.natashkinsasha.model.DaySchedule;
import by.natashkinsasha.service.ScheduleService;
import by.natashkinsasha.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/daySchedule")
public class DayScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    List<DaySchedule> getDaySchedules(@RequestParam("startWorkTime") @NotNull(message = "Start work time is null.") Long startWorkTime, @RequestParam("finishWorkTime") @NotNull(message = "Finish work time is null.") Long finishWorkTime, @RequestParam("startData") Long startData, @RequestParam("finishData") Long finishData) {
        return scheduleService.create(startData, finishData, startWorkTime, finishWorkTime);
    }
}
