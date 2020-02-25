package org.mflix.forecast.controller;

import javax.validation.Valid;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.service.UserService;
import org.mflix.forecast.view.ResponseView;
import org.mflix.forecast.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResponseComponent responseComponent;

    @PostMapping("/")
    public ResponseEntity<ResponseView> postByView(@Valid @RequestBody UserView userView) {
        userView = userService.createByView(userView);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, userView);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseView> getAll() {
        var userViewList = userService.readAll();
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, userViewList);
    }

    @GetMapping("/page/")
    public ResponseEntity<ResponseView> getAllWithPage(Pageable pageable) {
        var userViewPage = userService.readAllWithPage(pageable);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, userViewPage);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ResponseView> getById(@PathVariable long id) {
        var userView = userService.readById(id);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, userView);
    }

    @PutMapping("/{id}/")
    public ResponseEntity<ResponseView> putByIdAndView(@PathVariable long id, @Valid @RequestBody UserView userView) {
        userView = userService.updateByIdAndView(id, userView);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, userView);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<ResponseView> deleteById(@PathVariable long id) {
        userService.deleteById(id);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK);
    }
}