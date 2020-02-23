package org.mflix.forecast.component;

import org.mflix.forecast.enumeration.ResponseEnumeration;
import org.mflix.forecast.view.ResponseView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseComponent {
    public ResponseEntity<ResponseView> generate(ResponseEnumeration responseEnumeration, HttpStatus httpStatus) {
        ResponseView responseView = new ResponseView();
        responseView.setCode(responseEnumeration.getCode());
        responseView.setMessage(responseEnumeration.getMessage());
        return new ResponseEntity<>(responseView, httpStatus);
    }

    public ResponseEntity<ResponseView> generate(ResponseEnumeration responseEnumeration, HttpStatus httpStatus,
            Object object) {
        ResponseEntity<ResponseView> responseEntity = generate(responseEnumeration, httpStatus);
        responseEntity.getBody().setObject(object);
        return responseEntity;
    }
}