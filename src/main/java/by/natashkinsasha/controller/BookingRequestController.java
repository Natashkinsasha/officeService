package by.natashkinsasha.controller;

import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.model.DaySchedule;
import by.natashkinsasha.service.BookingRequestService;
import by.natashkinsasha.service.ScheduleService;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/bookingRequest")
public class BookingRequestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private BookingRequestService bookingRequestService;
    @Autowired
    private Validator validator;


    @Bean
    public Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @RequestMapping(method = RequestMethod.GET)
    Page<BookingRequest> getBookingRequests(@NotNull @RequestParam("pageNumber") Integer pageNumber, @NotNull @RequestParam("pageSize")  Integer pageSize, @NotNull @RequestParam("sortBy[]")  String[] sortBy, @NotNull @RequestParam("sortDirection")  String sortDirection, @RequestParam("startWorkTime") @NotNull(message = "Start work time is null.") Long startWorkTime, @RequestParam("finishWorkTime") @NotNull(message = "Finish work time is null.") Long finishWorkTime, @RequestParam("startData") Long startData, @RequestParam("finishData") Long finishData) {
        Page<BookingRequest> bookingRequestList = bookingRequestService.get(pageNumber, pageSize, sortBy, Sort.Direction.fromString(sortDirection), startData, finishData, startWorkTime, finishWorkTime);
        return bookingRequestList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "createWithArray")
    public List<BookingRequest> postBookingRequestCreateWithArray(@RequestBody @NotNull @NotEmpty @Size(min = 1) @Valid List<BookingRequest> bookingRequests) {
        Set<ConstraintViolation<BookingRequest>> usuallValidate = new HashSet<>();
        for (BookingRequest bookingRequest : bookingRequests) {
            Set<ConstraintViolation<BookingRequest>> validate = validator.validate(bookingRequest);
            usuallValidate.addAll(validate);
        }
        if (usuallValidate.size() != 0) {
            throw new ConstraintViolationException("", usuallValidate);
        }
        return bookingRequestService.save(bookingRequests);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void postBookingRequest(@Valid @NotNull @RequestBody BookingRequest bookingRequest) {
        bookingRequestService.save(bookingRequest);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllBookingRequests() {
        bookingRequestService.delete();
    }


}
