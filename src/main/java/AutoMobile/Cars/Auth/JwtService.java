package AutoMobile.Cars.Auth;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    @Value("${ec.private.key.file}")
    String KeyFile;
    @Value("${ec.public.key.file}")
    String keyfile1;


    public String generateToken(String userName) {
        //It give a current date and using su=ysten clock
        Instant instant = Instant.now();
        //It using current system clock and add current tdate to plus 1-month
        LocalDate localDate = LocalDate.now().plusMonths(1);
        return Jwts.builder()
                // JWT Claims payload to modify as desired.
                .claims()
                // This is the "subject" of the token, representing the user 
                .subject(userName) 
                //This stands for "issued at" and represents the timestamp
                .issuedAt(Date.from(instant)) 
                //This represents the expiration time of the JWT token
                .expiration(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())) 
                .and()
                // This "Private-key" used to generate the token
                .signWith(generatePrivateKey())
                // Build the JWT token and return String token
                .compact();
    }

    private PrivateKey generatePrivateKey() {
        // Get encode key from the "KeyFile", that is stored encode String
        byte[] tokenKey = getKey(KeyFile).getBytes(); 
        // Get decode byte from encode byte key
        byte[] secretKeyBytes = Base64.getDecoder().decode(tokenKey);
        // This reconstructs a "Private key" from decode byte
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(secretKeyBytes);

        try {
            // I want to rebuild (load) an Elliptic Curve (EC) key from raw bytes (like Base64-decoded data).
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            // It is used our "Base64-decoded data to generate "Private Key"
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomRuntimeException("Unable to generate Ec private key: " + e.getMessage());
        }
    }

    private PublicKey generatePublicKey1() {
        // Get encode key from the "KeyFile1", that is stored encode String
        String tokenKey = getKey(keyfile1); 
        // Get decode byte from encoded String (or) byte 
        byte[] secretKeyBytes = Base64.getDecoder().decode(tokenKey);
        // This reconstructs a "Public key" from decode byte
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(secretKeyBytes);

        try {
            // I want to rebuild (load) an Elliptic Curve (EC) key from raw bytes (like Base64-decoded data).
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            // It is used our "Base64-decoded data to generate "Public Key"
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomRuntimeException("Unable to generate Ec private key: " + e.getMessage());
        }
    }

    private String getKey(String file) {
        // This file variable stored our encode key data file path
        String fileName=file;
        String content = null;
        // It is used to we got our stored encode key data 
        StringBuilder builder = new StringBuilder();
        try {
            // There we define our file name
            FileReader fileReader = new FileReader(fileName);
            if (fileReader != null) {
                // It is used to we read our file data
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                // This is read our all datas and assign to the content varibale
                while ((content = bufferedReader.readLine()) != null) {
                    // We append our data and replace the empty space and old content to new content
                    builder.append(content.replace(System.lineSeparator(), "")
                            .replace("-----BEGIN PRIVATE KEY-----", "").replaceAll("-----END PRIVATE KEY-----", "")
                            .replace("-----BEGIN PUBLIC KEY-----", "").replaceAll("-----END PUBLIC KEY-----", ""));
                }
                bufferedReader.close();
            }
            return builder.toString();
        } catch (IOException e) {
            throw new CustomRuntimeException("Unimplemented method 'getKey'");
        }
    }

    public String getUsernameByToken(String token) {
        try {
            // We pass our token and Claims getSubject method referrence
            return extractClaims(token, Claims::getSubject);
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'getUsernameByToken'");
        }
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        try {
            // We pass our token and got Claims
            final Claims claims = extractAllClaims(token);
            // "claimResolver" is a "getSubject" function (usually a lambda)
            // calls the "getSubject" function used claims object.
            return claimResolver.apply(claims);
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'extractClaims'");
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    // This is used to we sets up the object that will parse and validate JWTs.
                    .parser()
                    // Verify the token using this "Public-key"
                    .verifyWith(generatePublicKey1())
                    // Finalizes the parser setup. Returns a JwtParser object ready to use.
                    .build()
                    // Parses the JWT string (token) assuming it is signed. 
                    // Returns a Jws<Claims> object: a signed token containing the claims payload.
                    .parseSignedClaims(token)
                    // Extracts the actual claims (i.e., payload) from the JWT.
                    .getPayload();
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'extractAllClaims'");
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            // Get username by "getUsernameByToken" method
            final String userName = getUsernameByToken(token);
            // This check our username is equal to the userDetails and check to validity
            return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'validateToken'");
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            // Tests if this date is before the current date.
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'isTokenExpired'");
        }
    }

    private Date extractExpiration(String token) {
        try {
            // Pass the token and Claims "getExpiration" method referrence
            return extractClaims(token, Claims::getExpiration);
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'extractExpiration'");
        }
    }

}
