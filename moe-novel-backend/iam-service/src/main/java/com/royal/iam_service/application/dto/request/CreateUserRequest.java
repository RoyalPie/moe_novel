package com.royal.iam_service.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "UserName cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&-+=()!?\"]).{8,128}$", message = "Password must be at least 8 characters long and contain at least one letter and one number one special character and one uppercase letter")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    private String firstName;

    private String lastName;

    @Past(message = "Date of birth must be a past date.")
    private LocalDate dateOfBirth;

    private String street;

    private String ward;

    private String district;

    private String city;

    private String phoneNumber;

    private List<CreateUserRoleRequest> userRole;

}