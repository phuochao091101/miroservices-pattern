package com.example.productservice.command.api.controller;


import com.example.productservice.command.api.commands.CreateProductCommand;
import com.example.productservice.command.api.commands.DeleteProductCommand;
import com.example.productservice.command.api.model.ProductRestModel;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    private final CommandGateway commandGateway;

    public ProductCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addProduct(@RequestBody ProductRestModel productRestModel) {

        CreateProductCommand createProductCommand =
                CreateProductCommand.builder()
                        .productId(UUID.randomUUID().toString())
                        .name(productRestModel.getName())
                        .price(productRestModel.getPrice())
                        .quantity(productRestModel.getQuantity())
                        .build();
        String result = commandGateway.sendAndWait(createProductCommand);
        return result;
    }
    @DeleteMapping ("/{id}")
    public String deleteProduct(@PathVariable String id){
        DeleteProductCommand deleteProductCommand= new DeleteProductCommand(id);
        commandGateway.sendAndWait(deleteProductCommand);
        return "delete: "+id;
    }
}
