package com.gabe.blog.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserForm {
    public static final String PHONE_REG = "[0-9]{10}";
    @NotBlank
    private String username;
    @NotBlank
    @Length(min=8)
    private String password;
    @NotBlank
    private String confirmPassword;

    @AssertTrue(message="密碼與確認密碼不相符")
    private boolean isValid() {
        return password.equals(confirmPassword);
    }

    @Email
    private String email;
    @Pattern(regexp = PHONE_REG)
    private String cellphone;

    public UserForm() {
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
