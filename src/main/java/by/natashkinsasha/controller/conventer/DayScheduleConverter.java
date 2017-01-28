package by.natashkinsasha.controller.conventer;

import by.natashkinsasha.model.DaySchedule;
import by.natashkinsasha.util.TimeUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class DayScheduleConverter {

    public static  class Serializer extends JsonSerializer<DaySchedule>{
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void serialize(DaySchedule daySchedule, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("date", daySchedule.getDate());
            jsonGenerator.writeArrayFieldStart("reservations");
            daySchedule.getReservations().stream().forEachOrdered((reservations) ->{
                try {
                    jsonGenerator.writeObject(reservations);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("",e);
                }
            });
            jsonGenerator.writeEndArray();

            jsonGenerator.writeEndObject();

        }
    }
}
