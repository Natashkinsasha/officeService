package by.natashkinsasha.repository;


import by.natashkinsasha.model.BookingRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRequestRepository extends MongoRepository<BookingRequest, String> {
}