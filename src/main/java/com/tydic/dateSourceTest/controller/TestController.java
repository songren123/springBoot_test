package com.tydic.dateSourceTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.dateSourceTest.service.TestService;

@Controller
@RequestMapping(value = "test")
public class TestController {
	@Autowired
	public TestService testService;
	
  @ResponseBody
  @GetMapping(value = "/test1")
  public String test1() {
	  testService.test();
	  return "test";
  }
  
   
  @ResponseBody
  @GetMapping(value = "/pushDcaAddByCrm")
  public String pushDcaAddByCrm() {
	  testService.pushDcaAddByCrm();
	  return "test";
  }
	
}
