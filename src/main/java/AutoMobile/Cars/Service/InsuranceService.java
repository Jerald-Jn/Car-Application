package AutoMobile.Cars.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import AutoMobile.Cars.Model.Insurance;

@Service
public class InsuranceService {

    @SuppressWarnings("deprecation")
    public Insurance setInsuranceTemp(Insurance insurance) {
        Instant i = Instant.now();
        Date date = new Date();
        insurance = Insurance.builder().typeOfInsurance(insurance.getTypeOfInsurance()).issueDate(date)
                .insuranceExpiryDate(Date.from(i.plus(date.getDate() * 24 * 60 * 60, ChronoUnit.SECONDS))).build();
        return insurance;
    }

}
