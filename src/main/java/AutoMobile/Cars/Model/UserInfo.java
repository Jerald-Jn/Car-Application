package AutoMobile.Cars.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String Address;
    private String phoneNo;
    private String email;
    private String gender;
    private String State;
    String city;
    private String pincode;
    @Override
    public String toString() {
        return "UserDetail [Address=" + Address + ", phoneNo=" + phoneNo + ", email=" + email + ", gender=" + gender
                + ", State=" + State + ", pincode=" + pincode + "]";
    }

    
}
