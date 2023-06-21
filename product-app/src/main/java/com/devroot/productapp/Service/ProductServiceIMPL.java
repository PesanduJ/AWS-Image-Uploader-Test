package com.devroot.productapp.Service;

import com.devroot.productapp.Entity.Product;
import com.devroot.productapp.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceIMPL implements ProductService{

    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductServiceIMPL(ImageService imageService, ProductRepository productRepository) {
        this.imageService = imageService;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) throws IOException {
        if(product.getImageFile() != null){
            MultipartFile imageFile = product.getImageFile();
            String imageKey = imageService.saveImage(imageFile);
            product.setImageKey(imageKey);
        }
        return productRepository.save(product);
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
