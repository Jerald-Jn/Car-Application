package AutoMobile.Cars.cloudinary;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Model.Cars;

@Service
public class CloudinaryService {

    @Autowired
    Cloudinary cloudinary;

    @SuppressWarnings("unchecked")
    public Object uploadImage(Cars car,List<MultipartFile> images, MultipartFile carImage,MultipartFile carLogo) throws CustomException, IOException {
        try {
            Object public_id=null;
            Map<String, Object> map = null;
            for (MultipartFile image : images) {
                map = cloudinary.uploader().upload(image.getBytes(), Map.of("folder", "car-image"));
                public_id = map.get("public_id");
                Object imageurl = map.get("url");
                if(car.getImages()==null){
                    car.setImages(new HashMap<>());
                }
                car.getImages().put(public_id.toString(), imageurl);
            }
            Object banner = cloudinary.uploader().upload(carImage.getBytes(), Map.of("folder", "car-image")).get("url");
            car.setCarImage(banner);
            Object logo = cloudinary.uploader().upload(carLogo.getBytes(), Map.of("folder", "car-image")).get("url");
            car.setCarLogo(logo);
            return car;
        } catch (IOException e) {
            throw new CustomException(e);
        }
    }

    public Object getImage() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
