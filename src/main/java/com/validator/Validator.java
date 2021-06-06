package com.validator;

import java.util.Map;

public interface Validator {

    Map<String, String> validate(Object object);
}
