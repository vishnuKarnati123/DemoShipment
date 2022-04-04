package com.exam.shipement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.exam.shipement.dto.OrderDetailsDto;
import com.exam.shipement.dto.ProductDto;

public interface ShipmentService {

	public ResponseEntity<String> getShipmentStatus(int orderId);

	public String addShipmentStatus(OrderDetailsDto order);

	public String updateShipmentStatus(OrderDetailsDto order);

	public String deleteStatus(int orderId);

}
