package com.test.controllers;

import javassist.compiler.ast.Pair;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.test.domain.Task;
import com.test.service.TaskService;


@RestController
public class TaskController {

	private static final Logger logger = Logger.getLogger(TaskController.class);
	private TaskService service;
	private final String okResponse = "{}";
	
	
	@Autowired
	TaskController(@Qualifier("taskService") TaskService service) {
		this.service = service;
	}	 
	
	@RequestMapping("/task/test")
	public String test(Model model) {
		return "view";
	}
		
	@RequestMapping("/task/create")
	public String add(@ModelAttribute Task task) {		
		service.add(task);
		return okResponse;
	}
	
	@RequestMapping("/task/search")
	public List search(String query) {
		return service.find(query);
	}
	
	@RequestMapping("/task/complete/{id}")
	public String complete(@PathVariable Integer id) {
		service.complete(id);
		return okResponse;
	}

	@RequestMapping("/task/list")
	public List list() {		
		return service.findAll();
	}
	
	class ErrorResponseObject {
		
	}
	
	class ErrorResponse {
		
		public String error;
		public String errorType;
		
		public ErrorResponse(String error, String type) {
			this.error = error;
			this.errorType = type;
		}		
	}
	
	class GenericErrorResponse extends ErrorResponse {		
		
		public GenericErrorResponse(Exception exception) {
			super(exception.getMessage(), "generic");
		}
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public GenericErrorResponse handleError(HttpServletRequest req, Exception exception) {		
		return new GenericErrorResponse(exception);
	}

	class ValidationViolationResponse extends ErrorResponse {

		public Map<String, String> violations;
		
		public ValidationViolationResponse(ConstraintViolationException exception) {
			
			super(exception.getMessage(), "validation_violation");
			violations = new HashMap<String, String>();
			
			Set<ConstraintViolation<?>> violationSet = 
					exception.getConstraintViolations();			
			for (ConstraintViolation<?> violation : violationSet) {				
				String name = violation.getPropertyPath().toString();
				String desc = violation.getMessage();				
				violations.put(name, desc);
			}			
		}
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ValidationViolationResponse handleFieldError(HttpServletRequest req, 
			ConstraintViolationException exception) {
		return new ValidationViolationResponse(exception); 
	}	

}