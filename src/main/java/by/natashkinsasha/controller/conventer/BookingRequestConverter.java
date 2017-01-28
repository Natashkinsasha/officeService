package by.natashkinsasha.controller.conventer;

import by.natashkinsasha.model.BookingRequest;
import by.natashkinsasha.util.TimeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Optional;

@JsonComponent
public class BookingRequestConverter {


    public static class Deserializer extends JsonDeserializer<BookingRequest> {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public BookingRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            BookingRequest bookingRequest = new BookingRequest();
            Optional.ofNullable(node.get("bookingDateTime")).ifPresent((bookingDateTime)->{
                bookingRequest.setBookingDateTime(bookingDateTime.asLong());
            });

            Optional.ofNullable(node.get("userId")).ifPresent((userId)->{
                bookingRequest.setUserId(userId.asText());
            });

            Optional.ofNullable(node.get("submissionTime")).ifPresent((submissionTime)->{
                bookingRequest.setStartSubmissionData(TimeUtil.toUnixTime(TimeUtil.toLocalDate(submissionTime.asLong())));
                bookingRequest.setStartSubmissionTime(TimeUtil.toSecond(TimeUtil.toLocalDateTime(submissionTime.asLong()).toLocalTime()));
            });

            Optional.ofNullable(node.get("duration")).ifPresent((duration)->{
                Optional.ofNullable(bookingRequest.getStartSubmissionTime()).ifPresent((submissionTime)->{
                    bookingRequest.setFinishSubmissionTime(submissionTime+duration.asLong());
                });
            });

            logger.debug(bookingRequest.toString());
            return bookingRequest;
        }


    }
}
