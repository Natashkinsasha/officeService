package by.natashkinsasha.model.comparator;


import by.natashkinsasha.model.BookingRequest;

import java.util.Comparator;

public class ComparatorBookingRequestByBookingDate implements Comparator<BookingRequest> {
    @Override
    public int compare(BookingRequest o1, BookingRequest o2) {
        if (o1.getBookingDateTime()<(o2.getBookingDateTime())){
            return -1;
        } else if(o1.getBookingDateTime()>(o2.getBookingDateTime())){
            return 1;
        }
        return 0;
    }
}
