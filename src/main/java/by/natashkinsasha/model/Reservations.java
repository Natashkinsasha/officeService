package by.natashkinsasha.model;


import by.natashkinsasha.controller.conventer.ReservationsConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalTime;


@Data
@JsonSerialize(using = ReservationsConverter.Serializer.class)
public class Reservations   {

  private Long startDuration;
  private Long finishDuration;
  private String userId;

  public Reservations() {
  }


  public Long getStartDuration() {
    return startDuration;
  }

  public void setStartDuration(Long startDuration) {
    this.startDuration = startDuration;
  }

  public Long getFinishDuration() {
    return finishDuration;
  }

  public void setFinishDuration(Long finishDuration) {
    this.finishDuration = finishDuration;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}

