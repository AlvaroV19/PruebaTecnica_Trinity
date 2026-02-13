package com.example.demo.IService;

import com.example.demo.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> findAll(); // Listar todos los productos

    Optional<Product> findById(Long id); // Buscar por id

    Product save(Product product); // Crear producto

    List<Product> findByCliente(Long clienteId); // Listar productos por cliente

    void cancelar(Long id); // Cancelar cuenta

    void delete(Long id); // Eliminar producto
}
