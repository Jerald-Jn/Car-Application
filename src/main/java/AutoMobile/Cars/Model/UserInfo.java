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
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNo;
    private String email;
    private String state;
    String city;
    private String pincode;
    @Override
    public String toString() {
        return "UserInfo [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", phoneNo="
                + phoneNo + ", email=" + email + ", state=" + state + ", city=" + city + ", pincode=" + pincode + "]";
    }
    
    
}
