package by.natashkinsasha.service;


import by.natashkinsasha.model.BookingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.List;

public interface BookingRequestService {
    void delete();

    void save(BookingRequest bookingRequest);

    Page<BookingRequest> get(Integer pageNumber, Integer pageSize, String sortBy, Sort.Direction sortDirection, Long startData, Long finishData, Long startWorkTime, Long finishWorkTime);

    List<BookingRequest> save(List<BookingRequest> bookingRequests);
}
