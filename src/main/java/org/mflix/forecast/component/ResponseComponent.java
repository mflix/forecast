package org.mflix.forecast.component;

import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.view.ResponseView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseComponent {
    public ResponseEntity<ResponseView> generate(int code, String message, HttpStatus httpStatus) {
        ResponseView responseView = new ResponseView(code, message);
        return new ResponseEntity<>(responseView, httpStatus);
    }

    public ResponseEntity<ResponseView> generate(StatusEnumeration statusEnumeration, HttpStatus httpStatus) {
        return generate(statusEnumeration.getCode(), statusEnumeration.getMessage(), httpStatus);
    }

    public ResponseEntity<ResponseView> generate(StatusEnumeration statusEnumeration, HttpStatus httpStatus,
            Object object) {
        ResponseEntity<ResponseView> responseEntity = generate(statusEnumeration, httpStatus);
        responseEntity.getBody().setObject(object);
        return responseEntity;
    }
}