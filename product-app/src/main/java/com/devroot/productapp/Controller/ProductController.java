package com.devroot.productapp.Controller;

import com.devroot.productapp.Entity.Product;
import com.devroot.productapp.Service.ImageService;
import com.devroot.productapp.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Scheduled(fixedDelay = 86400000) // Runs every 24 hours
    public void cleanupFailedMultipartUploads() {
        imageService.cleanupFailedMultipartUploads();
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        // Generate image URLs for each product
        for (Product product : products) {
            String imageKey = product.getImageKey();
            String imageUrl = "https://zoozoo-product-images.s3.ap-south-1.amazonaws.com/" + imageKey;
            product.setImageKey(imageUrl);
        }

        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            String imageKey = product.getImageKey();
            String imageUrl = "https://zoozoo-product-images.s3.ap-south-1.amazonaws.com/" + imageKey;
            product.setImageKey(imageUrl);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestParam("file") MultipartFile file, @RequestParam("product") String productJson) {
        try {
            // Parse the productJson string into a Product object using a JSON parser
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);

            // Handle the file upload and get the image key
            String imageKey = imageService.saveImage(file);

            // Update the Product object with the image key
            product.setImageKey(imageKey);

            // Save the product
            productService.saveProduct(product);

            return ResponseEntity.ok("Product created successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save product");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
