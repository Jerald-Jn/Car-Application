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
    private String address;
    private String phoneNo;
    private String email;
    private String state;
    String city;
    private String pincode;
    @Override
    public String toString() {
        return "UserDetail [Address=" + address + ", phoneNo=" + phoneNo + ", email=" + email + ", State=" + state + ", pincode=" + pincode + "]";
    }

    
}
