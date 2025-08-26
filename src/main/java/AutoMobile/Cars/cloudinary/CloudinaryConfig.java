package AutoMobile.Cars.cloudinary;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    // It gives the cloudinary object for upload and destory image
    @Bean
    public Cloudinary getCloudinary(){
        Map<String,Object> map=new HashMap<>();
        map.put("cloud_name", "dqbavxils");
        map.put("api_key", "917223792143642");
        map.put("api_secret", "klFSt-6xvS6dHmChkeB_fFClL_c");
        map.put("secure", true);
        return new Cloudinary(map);
    }
}
