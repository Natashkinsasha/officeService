package by.natashkinsasha.repository;


import by.natashkinsasha.model.BookingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRequestRepository extends MongoRepository<BookingRequest, String> {

    Page<BookingRequest> findByStartSubmissionDataBetweenAndStartSubmissionTimeGreaterThanAndFinishSubmissionTimeLessThan(Long startData, Long finishData, Long startWorkTime, Long finishWorkTime, Pageable request);

    List<BookingRequest> findByStartSubmissionDataBetweenAndStartSubmissionTimeGreaterThanAndFinishSubmissionTimeLessThan(Long startData, Long finishData, Long startWorkTime, Long finishWorkTime);

    List<BookingRequest> findByStartSubmissionData(Long startSubmissionData);


}