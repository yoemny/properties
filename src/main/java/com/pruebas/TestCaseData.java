package com.pruebas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestCaseData {
	private String environmentName;
	private String environmentUrl;
	private String deviceName;
	private String driverName;
	private String driverSrc;
	private String userUAT;

	public TestCaseData clone() { 
		return new TestCaseData(environmentName, 
				environmentUrl, 
				deviceName, 
				driverName, 
				driverSrc, 
				userUAT); 
	} 
}

