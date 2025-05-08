package com.pozwizd.prominadaadmin.models.branch;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchRequest {
    Long id;
    
    @Size(max = 50, message = "code.size")
    String code;
    
    @Pattern(regexp = "\\+380(50|66|95|99|67|68|96|97|98|63|93|73)[0-9]{7}",
            message = "phone.pattern")
    String phoneNumber;
    
    @NotBlank(message = "name.required")
    @Size(min = 2, max = 100, message = "name.size")
    String name;
    
    @Email(message = "email.format")
    @Size(max = 100, message = "email.size")
    String email;
    
    @Size(max = 255, message = "address.size")
    String address;

    MultipartFile imagePath;
}