package com.EmployeeApp.MyApp.Controller;

import com.EmployeeApp.MyApp.Service.impl.ImageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;


//    @GetMapping("/view-image/{id}")
//    public ResponseEntity<InputStreamResource> getImage(@PathVariable("id") long id) throws SQLException {
//        byte[] imageData = imageService.getImageById(id);
//        if (imageData == null) {
//            return ResponseEntity.notFound().build();
//        }
//        InputStream inputStream = new ByteArrayInputStream(imageData);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG); // Adjust the media type if needed
//        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
//    }
//
//    @GetMapping("/view-image/{id}")
//    public ResponseEntity<Resource> viewImage(@PathVariable Long id) throws SQLException {
//        byte[] imageData = imageService.getImageById(id);  // Fetch image from DB by ID
//
//        if (imageData == null) {
//            return ResponseEntity.notFound().build();  // Return 404 if image not found
//        }
//
//        Resource imageResource = (Resource) new ByteArrayResource(imageData);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"employee-image.png\"")
//                .contentType(MediaType.IMAGE_PNG)  // Adjust media type (e.g., JPEG) as per your image type
//                .body(imageResource);
//    }

    @GetMapping("/view-image/{id}/{image}")
    public ResponseEntity<ByteArrayResource> viewImage(@PathVariable Long id) {
        System.out.println("Requested image for ID: " + id);
        byte[] imageData;

        try {
            imageData = imageService.getImageById(id);
        } catch (SQLException e) {
            // Handle SQL exception, log it, etc.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (imageData == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(imageData);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"employee-image.png\"")
                .contentType(MediaType.IMAGE_PNG) // Change media type if required
                .body(resource);
    }


}
