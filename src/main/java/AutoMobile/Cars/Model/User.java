package AutoMobile.Cars.Model;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "User")
public class User {
    private UUID userId;
    @Id
    private String userName;
    private String password;
    private UserInfo userInfo;
    private Set<String> roles; 

    public void setUserId(UUID uuid) {
        this.userId = uuid;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", userInfo=" + userInfo
                + ", roles=" + roles + "]";
    }
}
