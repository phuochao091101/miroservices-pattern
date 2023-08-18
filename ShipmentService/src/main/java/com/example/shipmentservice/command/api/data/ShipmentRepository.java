package com.example.shipmentservice.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


public interface ShipmentRepository extends JpaRepository<Shipment,String> {
}
