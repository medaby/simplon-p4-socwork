package fr.medab.socwokbusiness.controller.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomError {

    private final Map<String, Collection<String>> fieldErrors = new HashMap<>();

    private final Collection<String> globalErrors = new ArrayList<>();

    public CustomError() {
        //
    }

    public void addFieldError(String fieldName, String errorCode) {
        Collection<String> codes = fieldErrors.get(fieldName);
        if (codes == null) {
            codes = new ArrayList<String>();
            fieldErrors.put(fieldName, codes);
        }
        codes.add(errorCode);
    }

    public Map<String, Collection<String>> getFieldErrors() {
        return fieldErrors;
    }

    public void addGlobalErrors(String code) {
        globalErrors.add(code);
    }

    public Collection<String> getGlobalErrors() {
        return globalErrors;
    }

    @Override
    public String toString() {
        return String.format("{fieldErrors=%s, globalErrors=%s}", fieldErrors, globalErrors);
    }
}
