package by.natashkinsasha.service;


import by.natashkinsasha.model.BookingRequest;

import java.util.List;

public interface BookingRequestService {
    void delete();
    void save(BookingRequest bookingRequest);
    void save(List<BookingRequest> bookingRequests);
}
