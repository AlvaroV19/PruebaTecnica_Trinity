package com.example.demo.Service;

import com.example.demo.DTO.ProductRequestDTO;
import com.example.demo.DTO.ProductResponseDTO;
import com.example.demo.Entity.Client;
import com.example.demo.Entity.Product;
import com.example.demo.IService.IProductService;
import com.example.demo.Repository.ClientRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ProductResponseDTO> findAll() {

        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> responseList = new ArrayList<>();

        for (Product product : products) {

            ProductResponseDTO response = new ProductResponseDTO();
            response.setId(product.getId());
            response.setTipoCuenta(product.getTipoCuenta());
            response.setNumeroCuenta(product.getNumeroCuenta());
            response.setEstado(product.getEstado());
            response.setSaldo(product.getSaldo());
            response.setExentaGmf(product.getExentaGmf());
            response.setFechaCreacion(product.getFechaCreacion());
            response.setFechaModificacion(product.getFechaModificacion());
            response.setClientId(product.getClient().getId());
            response.setClientNombre(product.getClient().getNombres());

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public ProductResponseDTO findById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setTipoCuenta(product.getTipoCuenta());
        response.setNumeroCuenta(product.getNumeroCuenta());
        response.setEstado(product.getEstado());
        response.setExentaGmf(product.getExentaGmf());
        response.setSaldo(product.getSaldo());
        response.setFechaCreacion(product.getFechaCreacion());
        response.setFechaModificacion(product.getFechaModificacion());
        response.setClientId(product.getClient().getId());
        response.setClientNombre(product.getClient().getNombres());

        return response;
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO dto) {

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!"AHORRO".equalsIgnoreCase(dto.getTipoCuenta()) &&
                !"CORRIENTE".equalsIgnoreCase(dto.getTipoCuenta())) {
            throw new RuntimeException("Tipo de cuenta inválido");
        }

        if ("AHORRO".equalsIgnoreCase(dto.getTipoCuenta())
                && dto.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Cuenta ahorro no puede iniciar con saldo negativo");
        }

        if ("CORRIENTE".equalsIgnoreCase(dto.getTipoCuenta())
                && dto.getSaldo().compareTo(new BigDecimal("-5000000")) < 0) {
            throw new RuntimeException("Cuenta corriente supera el sobregiro permitido");
        }

        Product product = new Product();
        product.setTipoCuenta(dto.getTipoCuenta());
        product.setSaldo(dto.getSaldo());
        product.setExentaGmf(calcularExentaGmf(dto.getTipoCuenta(), dto.getSaldo()));
        product.setNumeroCuenta(generarNumeroCuenta(dto.getTipoCuenta()));

        product.setFechaCreacion(LocalDateTime.now());
        product.setFechaModificacion(LocalDateTime.now());

        product.setClient(client);

        product.setEstado("ACTIVA");
        Product saved = productRepository.save(product);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setTipoCuenta(saved.getTipoCuenta());
        response.setSaldo(saved.getSaldo());
        response.setNumeroCuenta(saved.getNumeroCuenta());
        response.setExentaGmf(saved.getExentaGmf());
        response.setEstado(saved.getEstado());
        response.setFechaCreacion(saved.getFechaCreacion());
        response.setFechaModificacion(saved.getFechaModificacion());

        response.setClientId(client.getId());
        response.setClientNombre(client.getNombres());

        return response;
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        product.setTipoCuenta(dto.getTipoCuenta());
        product.setSaldo(dto.getSaldo());
        product.setExentaGmf(calcularExentaGmf(dto.getTipoCuenta(), dto.getSaldo()));
        product.setFechaModificacion(LocalDateTime.now());
        product.setClient(client);

        Product updated = productRepository.save(product);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(updated.getId());
        response.setTipoCuenta(updated.getTipoCuenta());
        response.setSaldo(updated.getSaldo());
        response.setEstado(updated.getEstado());
        response.setExentaGmf(updated.getExentaGmf());
        response.setNumeroCuenta(updated.getNumeroCuenta());
        response.setClientId(client.getId());
        response.setClientNombre(client.getNombres());

        return response;
    }

    @Override
    public List<ProductResponseDTO> findByClient(Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Product> products = productRepository.findByClient_Id(clientId);

        List<ProductResponseDTO> responseList = new ArrayList<>();

        for (Product product : products) {

            ProductResponseDTO response = new ProductResponseDTO();
            response.setId(product.getId());
            response.setTipoCuenta(product.getTipoCuenta());
            response.setNumeroCuenta(product.getNumeroCuenta());
            response.setExentaGmf(product.getExentaGmf());
            response.setSaldo(product.getSaldo());
            response.setEstado(product.getEstado());
            response.setFechaCreacion(product.getFechaCreacion());
            response.setFechaModificacion(product.getFechaModificacion());
            response.setClientId(client.getId());
            response.setClientNombre(client.getNombres());

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public void cancelar(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if ("CANCELADA".equalsIgnoreCase(product.getEstado())) {
            throw new RuntimeException("La cuenta ya está cancelada");
        }

        if (product.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("No se puede cancelar una cuenta con saldo diferente de cero");
        }

        product.setEstado("CANCELADA");

        productRepository.save(product);
    }

    private String generarNumeroCuenta(String tipoCuenta) {

        String prefijo = tipoCuenta.equalsIgnoreCase("AHORRO") ? "53" : "33";

        String numero;
        do {
            int random = (int)(Math.random() * 100000000);
            numero = prefijo + String.format("%08d", random);
        } while (productRepository.existsByNumeroCuenta(numero));

        return numero;
    }

    private Boolean calcularExentaGmf(String tipoCuenta, BigDecimal saldo) {
        if ("AHORRO".equalsIgnoreCase(tipoCuenta)) {
            // Ejemplo: exenta si saldo inicial < 350_000
            return saldo.compareTo(new BigDecimal("350000")) < 0;
        }
        // CORRIENTE no exenta
        return false;
    }

}
