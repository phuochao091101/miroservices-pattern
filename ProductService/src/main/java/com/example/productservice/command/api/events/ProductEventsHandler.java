package com.example.productservice.command.api.events;


import com.example.productservice.command.api.data.Product;
import com.example.productservice.command.api.model.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
public class ProductEventsHandler {

    private ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
        Product product =
                new Product();
        BeanUtils.copyProperties(event,product);
        productRepository.save(product);
//        throw new Exception("Exception Occurred");
    }

    @EventHandler
    public void on(ProductDeletedEvent productDeletedEvent) throws Exception {
        System.out.println(productDeletedEvent.getProductId());
        productRepository.deleteById(productDeletedEvent.getProductId());
        System.out.println("Deleted");
//        throw new Exception("Exception Deleting Occurred");
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
