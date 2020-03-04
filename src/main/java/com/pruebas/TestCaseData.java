package com.pruebas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseData {
	private String environment;
	private String device;
	private String driver;
	private String userUAT;
}
