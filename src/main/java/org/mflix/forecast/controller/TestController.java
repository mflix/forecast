package org.mflix.forecast.controller;

import javax.validation.Valid;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.service.TestService;
import org.mflix.forecast.view.ResponseView;
import org.mflix.forecast.view.TestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
public class TestController {
    private final TestService testService;
    private final ResponseComponent responseComponent;

    @Autowired
    public TestController(ResponseComponent responseComponent, TestService testService) {
        this.testService = testService;
        this.responseComponent = responseComponent;
    }

    // @PreAuthorize("hasRole('TEST')")
    @PostMapping("/")
    public ResponseEntity<ResponseView> postByView(@Valid @RequestPart TestView testView,
            @RequestPart MultipartFile file) {
        testView = testService.createByView(testView);
        testView.setFileName(file.getOriginalFilename());
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, testView);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseView> getAll() {
        var testViewList = testService.readAll();
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, testViewList);
    }

    @GetMapping("/page/")
    public ResponseEntity<ResponseView> getAllWithPage(Pageable pageable) {
        var testViewPage = testService.readAllWithPage(pageable);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, testViewPage);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ResponseView> getById(@PathVariable long id) {
        var testView = testService.readById(id);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, testView);
    }

    @PreAuthorize("hasRole('TEST')")
    @PutMapping("/{id}/")
    public ResponseEntity<ResponseView> putByIdAndView(@PathVariable long id, @Valid @RequestBody TestView testView) {
        testView = testService.updateByIdAndView(id, testView);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, testView);
    }

    @PreAuthorize("hasRole('TEST')")
    @DeleteMapping("/{id}/")
    public ResponseEntity<ResponseView> deleteById(@PathVariable long id) {
        testService.deleteById(id);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK);
    }

    // image/png
    @GetMapping("/file/{filename}")
    public ResponseEntity<FileSystemResource> getFile(@PathVariable String filename) {
        var file = testService.getFile(filename);
        return responseComponent.generate(file, HttpStatus.OK);
    }
}