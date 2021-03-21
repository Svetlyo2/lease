package org.softuni.lease1.validation;

import org.softuni.lease1.domain.model.binding.UserRegisterBindingModel;
import org.softuni.lease1.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserRegisterValidator implements Validator {
    private final UserRepository userRepository;

    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegisterBindingModel bindingModel = (UserRegisterBindingModel) o;
        if (bindingModel.getUsername().trim().isEmpty() ||
                bindingModel.getUsername().length()<3 ||
                bindingModel.getUsername().length()>15){
        errors.rejectValue("username", ValidationConstants.USERNAME_LENGTH, ValidationConstants.USERNAME_LENGTH);
        }
        if (this.userRepository.findByUsername(bindingModel.getUsername()).isPresent()){
            errors.rejectValue("username",
                    String.format(ValidationConstants.USERNAME_ALREADY_EXISTS, bindingModel.getUsername()),
                    String.format(ValidationConstants.USERNAME_ALREADY_EXISTS, bindingModel.getUsername()));
        }
        if (bindingModel.getPassword().trim().isEmpty() ||
                bindingModel.getPassword().length()<1 ||
                bindingModel.getPassword().length()>10){
            errors.rejectValue("password", ValidationConstants.PASSWORD_LENGTH, ValidationConstants.PASSWORD_LENGTH);
        }

        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())){
            errors.rejectValue("password", ValidationConstants.PASSWORDS_DO_NOT_MATCH, ValidationConstants.PASSWORDS_DO_NOT_MATCH);
        }
        if (bindingModel.getEmail().trim().isEmpty() ||
                bindingModel.getEmail().isBlank() ||
                bindingModel.getEmail().length()<5 ||
                bindingModel.getEmail().length()>20){
            errors.rejectValue("email", ValidationConstants.EMAIL_EMPTY, ValidationConstants.EMAIL_EMPTY);
        }

        if (!bindingModel.getEmail().trim().isEmpty() && this.userRepository.findByEmail(bindingModel.getEmail()).isPresent()){
            errors.rejectValue("email",
                    String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, bindingModel.getEmail()),
                    String.format(ValidationConstants.EMAIL_ALREADY_EXISTS, bindingModel.getEmail()));
        }
    }
}
