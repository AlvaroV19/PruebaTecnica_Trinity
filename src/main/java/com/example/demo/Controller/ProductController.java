package com.example.demo.Controller;

import com.example.demo.DTO.ProductRequestDTO;
import com.example.demo.DTO.ProductResponseDTO;
import com.example.demo.Entity.Product;
import com.example.demo.IService.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    // BUSCAR POR ID
    @GetMapping("/getProducto/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    // CREAR PRODUCTO (CUENTA)
    @PostMapping("/saveProducto")
    public ResponseEntity<ProductResponseDTO> save(
            @RequestBody ProductRequestDTO dto) {

        ProductResponseDTO response = productService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // LISTAR PRODUCTOS POR CLIENTE
    @GetMapping("/ProductosByCliente/{clientId}")
    public ResponseEntity<List<ProductResponseDTO>>  findByClient_Id(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(productService.findByClient(clientId));
    }

    // CANCELAR PRODUCTO(CUENTA)
    @PutMapping("/cancelar/{id}")
    public void cancelar(@PathVariable Long id) {
        productService.cancelar(id);
    }

    //ACTUALIZAR PRODUCTO
    @PutMapping("/updateProducto/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO dto) {

        return ResponseEntity.ok(productService.update(id, dto));
    }
}
