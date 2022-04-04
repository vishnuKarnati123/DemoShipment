package com.exam.shipement.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exam.shipement.dto.OrderDetailsDto;
import com.exam.shipement.dto.ProductDto;
import com.exam.shipement.service.ShipmentService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class ShipmentController {

	@Autowired
	ShipmentService shipmentService;

	@Autowired
	RestTemplate restTemplate;

	Logger log = LoggerFactory.getLogger(ShipmentController.class);

	@Operation(summary = "Get Shipment Status", description = "To get shipment Status ", tags = "Shipment")
	@GetMapping("/shipmentstatus/{orderId}")
	public ResponseEntity<String> getShipmentstatus(@PathVariable int orderId) {
		log.info("Entered in shipment service");
		log.debug("get shipemnt response for orderId{} :{}", orderId, shipmentService.getShipmentStatus(orderId));
		return shipmentService.getShipmentStatus(orderId);

	}

	@Operation(summary = "Create Shipment Status", description = "To add shipment Status ", tags = "Shipment")
	@PostMapping("/status")
	public String addShipmentStatus(@RequestBody OrderDetailsDto order) {

		log.info("Entered in shipment service");
		log.debug("create shipemnt response for orderId{} :{}", order.getOrderid(),
				shipmentService.addShipmentStatus(order));

		return shipmentService.addShipmentStatus(order);

	}

	@Operation(summary = "Update Shipment Status", description = "To update shipment status ", tags = "Shipment")
	@PutMapping("/status")
	public String updateShipmentStatus(@RequestBody OrderDetailsDto order) {
		log.info("Entered in shipment service");
		log.debug("update shipemnt response for orderId{} :{}", order.getOrderid(),
				shipmentService.updateShipmentStatus(order));
		return shipmentService.updateShipmentStatus(order);

	}

	@Operation(summary = "Delete Shipment Status", description = "To Delete shipment Status ", tags = "Shipment")
	@DeleteMapping("status/{orderId}")
	public String deleteStatus(@PathVariable int orderId) {
		log.info("Entered in shipment service");
		log.debug("Delete shipemnt response for orderId{} :{}", orderId, shipmentService.deleteStatus(orderId));
		return shipmentService.deleteStatus(orderId);
	}

}
