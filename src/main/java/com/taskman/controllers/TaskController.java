package com.taskman.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import com.taskman.service.Task;
import com.taskman.service.TaskService;


@RestController
public class TaskController {

    private static final Logger logger = Logger.getLogger(TaskController.class);
    private final String okResponse = "{}";
    private final TaskService service;


    @Autowired
    TaskController(@Qualifier("taskService") TaskService service) {
        this.service = service;
    }

    @RequestMapping("/tasks/test")
    public String test(Model model) {
        return "view";
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST, consumes = "application/json")
    public Task add(@RequestBody Task task) {
        return service.add(task);
    }

    @RequestMapping(value = "/tasks", params = "query")
    public Collection<? extends Task> search(String query) {
        return service.find(query);
    }

    @RequestMapping("/tasks/{id}/complete")
    public String complete(@PathVariable Integer id) {
        service.complete(id);
        return okResponse;
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public Collection<? extends Task> list() {
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public GenericErrorResponse handleError(HttpServletRequest req, Exception exception) {
        return new GenericErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationViolationResponse handleFieldError(HttpServletRequest req,
                                                        ConstraintViolationException exception) {
        return new ValidationViolationResponse(exception);
    }

    static class ErrorResponseObject {
    }

    static class ErrorResponse {

        public String error;
        public String errorType;

        public ErrorResponse(String error, String type) {
            this.error = error;
            this.errorType = type;
        }
    }

    static class GenericErrorResponse extends ErrorResponse {
        public GenericErrorResponse(Exception exception) {
            super(exception.getMessage(), "generic");
        }
    }

    static class ValidationViolationResponse extends ErrorResponse {

        public Map<String, String> violations;

        public ValidationViolationResponse(ConstraintViolationException exception) {
            super(exception.getMessage(), "validation_violation");

            Set<ConstraintViolation<?>> violationSet =
                    exception.getConstraintViolations();

            violations = violationSet.stream().collect(Collectors.toMap(
                    violation -> violation.getPropertyPath().toString(),
                    ConstraintViolation::getMessage
            ));
        }
    }

}