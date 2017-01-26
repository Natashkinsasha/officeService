package by.natashkinsasha.controller;

import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.model.DaySchedule;
import by.natashkinsasha.service.ScheduleService;
import by.natashkinsasha.util.TimeUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/bookingRequest")
public class BookingRequestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ScheduleService scheduleService;

    @Bean
    public Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }


    @RequestMapping(method = RequestMethod.POST, path = "createWithArray")
    public List<DaySchedule> postBookingRequestCreateWithArray(@RequestParam("startWorkTime") @NotNull(message = "Start work time is null.") Long startWorkTime, @RequestParam("finishWorkTime") @NotNull(message = "Finish work time is null.") Long finishWorkTime, @RequestBody @NotNull @NotEmpty @Size(min = 1) @Valid List<BookingRequest> bookingRequests) {
        Set<ConstraintViolation<List<BookingRequest>>> validate = getValidator().validate(bookingRequests);
        return scheduleService.create(TimeUtil.toLocalTime(startWorkTime), TimeUtil.toLocalTime(finishWorkTime), bookingRequests);
    }


    @RequestMapping(method = RequestMethod.POST)
    public void postBookingRequestCreate(@Valid @RequestBody BookingRequest bookingRequest) {

    }
}