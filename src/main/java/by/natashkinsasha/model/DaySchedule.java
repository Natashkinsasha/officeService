package by.natashkinsasha.model;


import by.natashkinsasha.controller.conventer.DayScheduleConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@JsonSerialize(using = DayScheduleConverter.Serializer.class)
public class DaySchedule   {
  private Long date;
  private List<Reservations> reservations = new ArrayList<Reservations>();

  public DaySchedule() {
  }


  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }

  public List<Reservations> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservations> reservations) {
    this.reservations = reservations;
  }
}

