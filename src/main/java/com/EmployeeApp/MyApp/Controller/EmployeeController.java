package com.EmployeeApp.MyApp.Controller;

import com.EmployeeApp.MyApp.Entity.EmployeeEntity;
import com.EmployeeApp.MyApp.Service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.IMAGE_PNG;

@Controller
public class EmployeeController {

    @Value("${upload.dir}")
    private String uploadDir;
    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private EmployeeService employeeService;


//    @GetMapping("/employee")
//    public String listEmployee(Model model ){
//
//       List<EmployeeEntity> employee = employeeService.getAllEmployee();
//        model.addAttribute("employee",employee );
//                return "employee.html";
//    }

    @GetMapping("/employee")
    public String listEmployee(Model model) {
        // Fetch all employees from the service
        List<EmployeeEntity> employee = employeeService.getAllEmployee();

        // Add them to the model under the name 'employee' (must match the template)
        model.addAttribute("employee", employee);

        // Return the Thymeleaf template without the .html extension
        return "employee";
    }


//    @GetMapping("/employee/new")
//    public String createEmployeeForm(Model model){
//        // create Employee object to hold Employee form data
//        EmployeeEntity employee = new EmployeeEntity();
//        m       caddAttribute("employee",employee);
//        return  "create_employee";
//
//    }

    @GetMapping("/employee/new")
    public String createEmployeeForm(Model model) {
        // Create a new employee object to hold the form data
        EmployeeEntity employee = new EmployeeEntity();
        model.addAttribute("employee", employee);
        return "create_employee";
    }

//    @PostMapping("/employee")
//    public String saveEmployee(@ModelAttribute("employee") EmployeeEntity employee){
//        employeeService.saveEmployee(employee);
//        return "redirect:/employee";
//    }
//
//    @PostMapping("/employee")
//    public String saveEmployee(@ModelAttribute("employee") EmployeeEntity employee, RedirectAttributes redirectAttributes) {
//        // Save employee to the database
//        employeeService.saveEmployee(employee);
//
//        // Add success message to RedirectAttributes
//        redirectAttributes.addFlashAttribute("successMessage", "Employee details successfully created");
//
//        // Redirect to employee list page
//        return "redirect:/employee";
//    }




    @PostMapping("/employee")
    public String saveEmployee(@ModelAttribute("employee") EmployeeEntity employee,
                               @RequestParam("employeeImage") MultipartFile imageFile,
                               RedirectAttributes redirectAttributes) {

        // Check if a file was uploaded
        if (!imageFile.isEmpty()) {
            try {
                // Define the path to save the image (you can adjust this path as per your requirements)
                String uploadDir = "src/main/resources/static/image/";
                String fileName = imageFile.getOriginalFilename();

                // Save the file locally
                Path path = Paths.get(uploadDir + fileName);
                Files.write(path, imageFile.getBytes());

                // Save the image path in the employee entity (you can store the file path or URL)
                employee.setImage(("/image/" + fileName).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Image upload failed!");
                return "redirect:/employee/new";
            }
        }

        // Save the employee data in the database
        employeeService.saveEmployee(employee);

        redirectAttributes.addFlashAttribute("successMessage", "Employee created successfully!");
        return "redirect:/employee";
    }



//    @GetMapping("/employee/edit/{id}")
//    public String editEmployeeForm(@PathVariable Long id,Model model){
//
//       //model.addAttribute("message", "Employee added successfully");
//
//
//        return "edit_employee";
//    }

    @GetMapping("/employee/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit_employee";
    }


//    @PostMapping("/employee/{id}")
//    public String updateEmployee(@PathVariable Long id, @ModelAttribute("employee") EmployeeEntity employee, RedirectAttributes redirectAttributes) {
//        EmployeeEntity existingEmployee = employeeService.getEmployeeById(id);
//
//        existingEmployee.setFirstName(employee.getFirstName());
//        existingEmployee.setLastname(employee.getLastname());
//        existingEmployee.setEmail(employee.getEmail());
//
//        employeeService.updateEmployee(existingEmployee);
//
//        redirectAttributes.addFlashAttribute("message", "Employee updated successfully");
//        return "redirect:/employee";
//    }

    @PostMapping("/employee/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @ModelAttribute("employee") EmployeeEntity employee,
                                 @RequestParam(value = "employeeImage", required = false) MultipartFile imageFile,
                                 RedirectAttributes redirectAttributes) {

        EmployeeEntity existingEmployee = employeeService.getEmployeeById(id);

        // Update the employee details
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastname(employee.getLastname());
        existingEmployee.setEmail(employee.getEmail());

        // Handle the image upload if a new image is provided
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Save the image to the database (in binary format)
                existingEmployee.setImage(imageFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Image upload failed!");
                return "redirect:/employee/edit/" + id;
            }
        }

        // Update the employee in the database
        employeeService.updateEmployee(existingEmployee);

        redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully!");
        return "redirect:/employee";
    }



//
//    @GetMapping("/clear-message")
//    public String clearMessage(SessionStatus status) {
//        status.setComplete();
//        return "redirect:/employee"; // redirect to the same page after message removal
//    }

//    @PostMapping("/employee/{id}")
//    public String updateEmployee(@PathVariable Long id ,@ModelAttribute("employee") EmployeeEntity employee,Model model){
//        // get student from database by id
//
//        //model.addAttribute("message", "Employee updated successfully");
//
//        EmployeeEntity existingEmployee = employeeService.getEmployeeById(id);
//
//        existingEmployee.setId(employee.getId());
//        existingEmployee.setFirstName(employee.getFirstName());
//        existingEmployee.setLastname(employee.getLastname());
//        existingEmployee.setEmail(employee.getEmail());
//
//        // save updated employee object
//        employeeService.updateEmployee(existingEmployee);
//        return "redirect:/employee";
//
//    }

    // handler method to handle delete Employee request

//    @GetMapping("/employee/{id}")
//    public String deleteEmployee(@PathVariable Long id,Model model){
//      // model.addAttribute("message", "Employee deleted successfully");
//
//        employeeService.deleteEmployeeById(id);
//     return "redirect:/employee";
//    }

    @GetMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployeeById(id);
        redirectAttributes.addFlashAttribute("message", "Employee deleted successfully");
        return "redirect:/employee";
    }



    @PostMapping("/upload")
    public String handleImageUpload(@RequestParam("employeeImage") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select an image to upload.");
            return "redirect:/employee";
        }

        try {
            // Create the uploads directory if it does not exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file locally in the specified directory
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);

            // Add success message to redirectAttributes
            redirectAttributes.addFlashAttribute("message", "Image uploaded successfully: " + file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Image upload failed.");
        }

        return "redirect:/employee";
    }





        @GetMapping("/{filename}")
        public ResponseEntity<Resource> getImage(@PathVariable String filename) {
            File file = new File(uploadDir + filename);
            if (file.exists()) {
                Resource resource = (Resource) new FileSystemResource(file);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, String.valueOf(IMAGE_PNG)) // Adjust the content type as necessary
                        .body(resource);
            }
            return ResponseEntity.notFound().build();
        }


//    @GetMapping("/employee/image/{id}")
//    public ResponseEntity<byte[]> getEmployeeImage(@PathVariable Long id) {
//        EmployeeEntity employee = employeeService.getEmployeeById(id);
//        if (employee != null && employee.getImage() != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG); // or MediaType.IMAGE_PNG if you're using PNG
//            return new ResponseEntity<>(employee.getImage(), headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


//    @GetMapping("/employee/image/{id}")
//    public ResponseEntity<byte[]> getEmployeeImage(@PathVariable Long id) {
//        EmployeeEntity employee = employeeService.getEmployeeById(id); // Fetch the employee by id
//
//        if (employee != null && employee.getImage() != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_PNG); // Assuming the image is JPEG
//            headers.setContentLength(employee.getImage().length); // Set content length
//            return new ResponseEntity<>(employee.getImage(), headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/employee/image/{id}")
    public ResponseEntity<byte[]> getEmployeeImage(@PathVariable Long id) {
        byte[] employeeImage = employeeService.getEmployeeImageById(id);

        if (employeeImage != null) {
            System.out.println("Image byte size: " + employeeImage.length);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Directly use MediaType.IMAGE_PNG
            headers.setContentLength(employeeImage.length);
            return new ResponseEntity<>(employeeImage, headers, HttpStatus.OK);
        } else {
            System.out.println("Employee or Image Not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





}




