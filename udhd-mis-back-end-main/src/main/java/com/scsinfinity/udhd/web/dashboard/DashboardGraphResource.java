package com.scsinfinity.udhd.web.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.dashboard.dto.DashboardDTO;
import com.scsinfinity.udhd.services.dashboard.interfaces.IDashboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardGraphResource {

	private final IDashboardService dashboardService;

	@GetMapping
	public ResponseEntity<DashboardDTO> getDashboardData() {
		return ResponseEntity.ok(dashboardService.getDashboardData());
	}

}
