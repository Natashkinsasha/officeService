package by.natashkinsasha.model;


import by.natashkinsasha.controller.conventer.BookingRequestConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Validated
@JsonDeserialize(using = BookingRequestConverter.Deserializer.class)
public class BookingRequest {
    @Id
    private String id;
    @NotNull
    private Long bookingDateTime;
    @NotNull
    @NotBlank
    @Size(max=10)
    private String userId;
    @NotNull
    private Long startSubmissionData;
    @NotNull
    private Long startSubmissionTime;
    @NotNull
    private Long finishSubmissionTime;

    public BookingRequest() {
    }

    @AssertTrue(message = "Booking time should be before start submission time")
    private boolean isСorrectlyBookingTime() {
        return bookingDateTime < (startSubmissionData+startSubmissionTime);
    }

    @AssertTrue(message = "Start submission time should be before finish submission time")
    private boolean isСorrectlySubmissionTime() {
        return startSubmissionTime < (finishSubmissionTime);
    }

    public boolean isOverlapping(BookingRequest bookingRequest) {
        return this.startSubmissionData.equals(bookingRequest.startSubmissionData) && this.getStartSubmissionTime()<bookingRequest.getFinishSubmissionTime() && bookingRequest.getStartSubmissionTime()<this.getFinishSubmissionTime();
    }


    public Long getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(Long bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getStartSubmissionData() {
        return startSubmissionData;
    }

    public void setStartSubmissionData(Long startSubmissionData) {
        this.startSubmissionData = startSubmissionData;
    }

    public Long getFinishSubmissionTime() {
        return finishSubmissionTime;
    }

    public void setFinishSubmissionTime(Long finishSubmissionTime) {
        this.finishSubmissionTime = finishSubmissionTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStartSubmissionTime() {
        return startSubmissionTime;
    }

    public void setStartSubmissionTime(Long startSubmissionTime) {
        this.startSubmissionTime = startSubmissionTime;
    }
}

