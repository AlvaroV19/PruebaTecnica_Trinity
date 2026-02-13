package com.example.demo.Controller;

import com.example.demo.Entity.Product;
import com.example.demo.IService.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/productos")
public class ProductController {

    @Autowired
    private IProductService productService;

    // LISTAR TODOS
    @GetMapping("/getProductos")
    public List<Product> findAll() {
        return productService.findAll();
    }

    // BUSCAR POR ID
    @GetMapping("/getProducto/{id}")
    public Optional<Product> findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    // CREAR PRODUCTO (CUENTA)
    @PostMapping("/saveProducto")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    // LISTAR PRODUCTOS POR CLIENTE
    @GetMapping("/getProductosByCliente/{clienteId}")
    public List<Product> findByCliente(@PathVariable Long clienteId) {
        return productService.findByCliente(clienteId);
    }

    // CANCELAR CUENTA
    @PutMapping("/cancelar/{id}")
    public void cancelar(@PathVariable Long id) {
        productService.cancelar(id);
    }

    // ELIMINAR PRODUCTO
    @DeleteMapping("/deleteProducto/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
