package com.example.demo.Service;

import com.example.demo.Entity.Client;
import com.example.demo.Entity.Product;
import com.example.demo.IService.IProductService;
import com.example.demo.Repository.ClientRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {

      if (product.getTipoCuenta().equals("AHORRO")
                && product.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Cuenta ahorro no puede iniciar con saldo negativo");
        }

        Client client = clientRepository.findById(product.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        product.setClient(client);
        product.setNumeroCuenta(generarNumero(product.getTipoCuenta()));
        product.setEstado("ACTIVA");

        return productRepository.save(product);
    }

    @Override
    public List<Product> findByCliente(Long clientId) {
        return productRepository.findByClient_Id(clientId);
    }

    @Override
    public void cancelar(Long id) {

        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("Saldo debe ser 0 para cancelar");
        }

        producto.setEstado("CANCELADA");
        productRepository.save(producto);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private String generarNumero(String tipo) {
        String prefijo = tipo.equals("AHORRO") ? "53" : "33";
        long consecutivo = productRepository.count() + 1;
        return prefijo + String.format("%08d", consecutivo);
    }
}
