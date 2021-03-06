package by.natashkinsasha.service;


import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.model.comparator.ComparatorBookingRequestByBookingDate;
import by.natashkinsasha.repository.BookingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingRequestServiceImpl implements BookingRequestService {
    @Autowired
    private BookingRequestRepository bookingRequestRepository;


    @Override
    public Page<BookingRequest> get(Integer pageNumber, Integer pageSize, String[] sortBy, Sort.Direction sortDirection, Long startData, Long finishData, Long startWorkTime, Long finishWorkTime) {
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize, sortDirection, sortBy);
        return bookingRequestRepository.findByStartSubmissionDataBetweenAndStartSubmissionTimeGreaterThanAndFinishSubmissionTimeLessThan( startData, finishData, startWorkTime - 1, finishWorkTime + 1, pageRequest);
    }

    @Override
    public void delete() {
        bookingRequestRepository.deleteAll();
    }

    @Override
    public void save(BookingRequest bookingRequest) {
        List<BookingRequest> bookingRequests = bookingRequestRepository.findByStartSubmissionData(bookingRequest.getStartSubmissionData());
        for (BookingRequest oldBookingRequest : bookingRequests) {
            if (oldBookingRequest.isOverlapping(bookingRequest)) {
                return;
            }
        }
        bookingRequestRepository.save(bookingRequest);
    }




    @Override
    public List<BookingRequest> save(List<BookingRequest> bookingRequests) {
        final List<BookingRequest> saveList = new ArrayList<>();
        final List<BookingRequest> notSaveList = new ArrayList<>();
        //Разбиваем новые бронирования по дням
        Map<Long, List<BookingRequest>> groupBySubmissionData = bookingRequests.parallelStream().collect(Collectors.groupingBy((bookingRequest) -> bookingRequest.getStartSubmissionData()));

        groupBySubmissionData.entrySet().parallelStream().forEach(entry -> {
            //Находим в базе, все бронирования на день
            List<BookingRequest> oldBookingRequests = bookingRequestRepository.findByStartSubmissionData(entry.getKey());
            for (BookingRequest newBookingRequest : entry.getValue()) {
                //Проверяем пересекаются ли новое бронирование с бронированиями которые уже лежат в базе
                loopHolder(newBookingRequest, oldBookingRequests, saveList, notSaveList);
            }
        });
        // Удаляем пересекающие бронирования в груповом запросе
        removeOverlapping(saveList, notSaveList);
        bookingRequestRepository.save(saveList);
        return notSaveList;
    }

    private void loopHolder(BookingRequest newBookingRequest, List<BookingRequest> oldBookingRequests, List<BookingRequest> saveList, List<BookingRequest> notSaveList) {
        for (BookingRequest oldBookingRequest : oldBookingRequests) {
            if (newBookingRequest.isOverlapping(oldBookingRequest)) {
                notSaveList.add(newBookingRequest);
                return;
            }
        }
        saveList.add(newBookingRequest);
    }

    private void removeOverlapping(List<BookingRequest> saveList, List<BookingRequest> notSaveList) {
        saveList.sort(new ComparatorBookingRequestByBookingDate());
        int size = saveList.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (saveList.get(j).isOverlapping(saveList.get(i))) {
                    notSaveList.add(saveList.get(j));
                    saveList.remove(j);
                    j--;
                    size--;
                }
            }
        }
    }
}
