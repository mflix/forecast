package org.mflix.forecast.component;

import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.view.ResponseView;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseComponent {
    public ResponseEntity<ResponseView> generate(int code, String message, HttpStatus httpStatus) {
        var responseView = new ResponseView(code, message);
        return new ResponseEntity<>(responseView, httpStatus);
    }

    public ResponseEntity<ResponseView> generate(StatusEnumeration statusEnumeration, HttpStatus httpStatus) {
        return generate(statusEnumeration.getCode(), statusEnumeration.getMessage(), httpStatus);
    }

    public ResponseEntity<ResponseView> generate(StatusEnumeration statusEnumeration, HttpStatus httpStatus,
            String cause) {
        var responseEntity = generate(statusEnumeration, httpStatus);
        responseEntity.getBody().setCause(cause);
        return responseEntity;
    }

    public ResponseEntity<ResponseView> generate(StatusEnumeration statusEnumeration, HttpStatus httpStatus,
            Object object) {
        var responseEntity = generate(statusEnumeration, httpStatus);
        responseEntity.getBody().setObject(object);
        return responseEntity;
    }

    public ResponseEntity<FileSystemResource> generate(FileSystemResource fileSystemResource, HttpStatus httpStatus) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(fileSystemResource, httpHeaders, httpStatus);
    }
}