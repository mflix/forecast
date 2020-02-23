package org.mflix.forecast.controller;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.enumeration.ResponseEnumeration;
import org.mflix.forecast.service.TestService;
import org.mflix.forecast.view.ResponseView;
import org.mflix.forecast.view.TestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private ResponseComponent responseComponent;

    @PreAuthorize("hasRole('TEST')")
    @PostMapping("/")
    public ResponseEntity<ResponseView> post(@RequestBody TestView testView) {
        testView = testService.create(testView);
        return responseComponent.generate(ResponseEnumeration.S0, HttpStatus.OK, testView);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseView> getAll() {
        var testViewList = testService.readAll();
        return responseComponent.generate(ResponseEnumeration.S0, HttpStatus.OK, testViewList);
    }

    @GetMapping("/page/")
    public ResponseEntity<ResponseView> getAllWithPage(Pageable pageable) {
        var testViewPage = testService.readAllWithPage(pageable);
        return responseComponent.generate(ResponseEnumeration.S0, HttpStatus.OK, testViewPage);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ResponseView> get(@PathVariable long id) {
        TestView testView = testService.read(id);
        return responseComponent.generate(ResponseEnumeration.S0, HttpStatus.OK, testView);
    }

    @PreAuthorize("hasRole('TEST')")
    @PutMapping("/{id}/")
    public ResponseEntity<ResponseView> put(@PathVariable long id, @RequestBody TestView testView) {
        testView = testService.update(id, testView);
        return responseComponent.generate(ResponseEnumeration.S0, HttpStatus.OK, testView);
    }

    @PreAuthorize("hasRole('TEST')")
    @DeleteMapping("/{id}/")
    public ResponseEntity<ResponseView> delete(@PathVariable long id) {
        testService.delete(id);
        return responseComponent.generate(ResponseEnumeration.S0, HttpStatus.OK);
    }
}