package AutoMobile.Cars.cloudinary;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import AutoMobile.Cars.Excrptionfold.CustomException;

@Service
public class CloudinaryService {

    @Autowired
    Cloudinary cloudinary;
    
    public Object uploadImage(MultipartFile image) throws CustomException{
        try {
            Map<?,?> map=cloudinary.uploader().upload(image.getBytes(), Map.of());
            String image_url="url";
            Object obj=map.get(image_url);
            System.err.println("map -> "+map);
            
            return obj;
        } catch (IOException e) {
            throw new CustomException(e);
        }
    }

    public Object getImage(){
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
