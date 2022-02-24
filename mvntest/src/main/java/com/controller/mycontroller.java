package com.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mycontroller {

	@GetMapping(path = "/api")
	public String getParam(
								@RequestParam(name="param1", defaultValue="true") String param1,
								@RequestParam(name="param2", defaultValue="false") String param2			
			
			) {
		return "param1:  "+param1+", param2  ";
	}
}
