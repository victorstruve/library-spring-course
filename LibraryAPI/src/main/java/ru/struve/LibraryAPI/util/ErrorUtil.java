package ru.struve.LibraryAPI.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorUtil {
    public static void returnErrorsToClient(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        errors.forEach(err-> errorMsg.append(err.getField())
                .append(" - ")
                .append(err.getDefaultMessage() == null ? err.getCode():err.getDefaultMessage())
                .append("; "));
        throw new ClientException(errorMsg.toString());
    }
}
