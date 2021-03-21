package org.softuni.lease1.validation;

import org.softuni.lease1.domain.entity.User;
import org.softuni.lease1.domain.model.binding.UserEditBindingModel;
import org.softuni.lease1.domain.model.binding.UserRegisterBindingModel;
import org.softuni.lease1.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserEditValidator implements Validator {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEditValidator(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterBindingModel.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        UserEditBindingModel bindingModel = (UserEditBindingModel) o;
        User user = this.userRepository.findByUsername(bindingModel.getUsername()).orElse(null);

        if (!this.bCryptPasswordEncoder.matches(bindingModel.getOldPassword(), user.getPassword())){
            errors.rejectValue("oldPassword", ValidationConstants.WRONG_PASSWORD, ValidationConstants.WRONG_PASSWORD);
        }

        if (!bindingModel.getPassword().trim().isEmpty() &&
                (bindingModel.getPassword().length()<1 ||
                bindingModel.getPassword().length()>10)){
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
