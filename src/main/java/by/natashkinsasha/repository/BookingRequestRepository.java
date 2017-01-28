package by.natashkinsasha.repository;


import by.natashkinsasha.model.BookingRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRequestRepository extends MongoRepository<BookingRequest, String>{

    List<BookingRequest> findByStartSubmissionDataBetweenAndStartSubmissionTimeGreaterThanAndFinishSubmissionTimeLessThan(Long startData, Long finishData, Long startWorkTime, Long finishWorkTime);

}