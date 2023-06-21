package com.devroot.productapp.Service;

import com.devroot.productapp.Entity.Product;
import com.devroot.productapp.Repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product) throws IOException;

    void deleteProduct(Long id);
}
