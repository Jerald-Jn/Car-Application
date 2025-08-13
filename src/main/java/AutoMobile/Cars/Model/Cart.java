package AutoMobile.Cars.Model;

import java.util.Map;

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
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart_for_car")
public class Cart {
    @Id
    String id;
    String user;
    Map<String,Integer> items;

    public Cart(String user,Map<String,Integer>  items){
        this.user=user;
        this.items=items;
    }
}
