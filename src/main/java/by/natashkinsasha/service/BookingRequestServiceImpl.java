package by.natashkinsasha.service;


import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.model.comparator.ComparatorBookingRequestByBookingDate;
import by.natashkinsasha.repository.BookingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingRequestServiceImpl implements BookingRequestService{
    @Autowired
    private BookingRequestRepository bookingRequestRepository;
    @Override
    public void delete() {
        bookingRequestRepository.deleteAll();
    }

    @Override
    public void save(BookingRequest bookingRequest) {
        bookingRequestRepository.save(bookingRequest);
    }

    @Override
    public void save(List<BookingRequest> bookingRequests) {
        List<BookingRequest> bookingRequestList = removeOverlapping(bookingRequests);
        bookingRequestRepository.save(bookingRequestList);
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
}
