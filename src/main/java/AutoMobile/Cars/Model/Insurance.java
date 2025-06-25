package AutoMobile.Cars.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Insurance {
    private Date insuranceExpiryDate;
    private Date issueDate;
    private String typeOfInsurance;
}
