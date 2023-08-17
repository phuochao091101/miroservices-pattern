package com.example.productservice.command.api.aggregate;

import com.example.productservice.command.api.commands.CreateProductCommand;
import com.example.productservice.command.api.commands.DeleteProductCommand;
import com.example.productservice.command.api.events.ProductCreatedEvent;
import com.example.productservice.command.api.events.ProductDeletedEvent;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        //You can perform all the validations
        ProductCreatedEvent productCreatedEvent =
                new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }
    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand) {
        //You can perform all the validations
        ProductDeletedEvent productDeletedEvent =
                new ProductDeletedEvent();
        BeanUtils.copyProperties(deleteProductCommand,productDeletedEvent);
        AggregateLifecycle.apply(productDeletedEvent);
    }

    public ProductAggregate() {
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.quantity = productCreatedEvent.getQuantity();
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.name = productCreatedEvent.getName();
    }
    @EventSourcingHandler
    public void on(ProductDeletedEvent productDeletedEvent) {
        this.productId = productDeletedEvent.getProductId();
    }
}
