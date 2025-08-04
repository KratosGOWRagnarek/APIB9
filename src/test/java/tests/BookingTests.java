package tests;

import org.testng.annotations.Test;
import pojosOrbeans.BookingDates;
import pojosOrbeans.BookingInfo;

public class BookingTests {
    @Test
    public void verifyCreateBooking(){
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckIn("2025-06-01");
        bookingDates.setCheckOut("2025-06-02");

        BookingInfo bookingInfo = new BookingInfo();
        bookingInfo.setFirstname("Sam");
        bookingInfo.setLastname("White");
        bookingInfo.setTotalprice(777);
        bookingInfo.setDepositpaid(true);
        bookingInfo.setAdditionalmeeds("blanket");
        bookingInfo.setBookingdates(bookingDates);

    }
}
