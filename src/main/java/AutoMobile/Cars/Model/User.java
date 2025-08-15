package AutoMobile.Cars.Model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.mongodb.core.mapping.DBRef;

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
@Document(collection = "User-Car-DB")
public class User {
    private UUID userId;
    @Id
    private String userName;
    private String password;
    private UserInfo userInfo;
    // @DBRef(lazy = true)
    private Car car;

    public void setUserId(UUID uuid) {
        this.userId = uuid;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", userInfo=" + userInfo
                + ", car=" + car + "]";
    }

    
}
