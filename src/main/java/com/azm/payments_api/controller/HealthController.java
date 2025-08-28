/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.azm.payments_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author azm
 */
@RestController
public class HealthController
{
	@GetMapping("/ping")
	public String ping()
	{
		return "Payments API is running 🚀";
	}
}
