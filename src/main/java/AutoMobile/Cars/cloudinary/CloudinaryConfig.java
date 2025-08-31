package AutoMobile.Cars.cloudinary;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.key}")
    String cloudinaryKey;
    @Value("${cloudinary.secret}")
    String cloudinarySecret;

    // It gives the cloudinary object for upload and destory image
    @Bean
    public Cloudinary getCloudinary(){
        Map<String,Object> map=new HashMap<>();
        map.put("cloud_name", "dqbavxils");
        map.put("api_key", cloudinaryKey);
        map.put("api_secret", cloudinarySecret);
        map.put("secure", true);
        return new Cloudinary(map);
    }
}
