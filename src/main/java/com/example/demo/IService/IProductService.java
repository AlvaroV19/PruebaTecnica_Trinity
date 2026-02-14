package com.example.demo.IService;

import com.example.demo.DTO.ProductRequestDTO;
import com.example.demo.DTO.ProductResponseDTO;

import java.util.List;

public interface IProductService {

    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    ProductResponseDTO save(ProductRequestDTO dto);
    List<ProductResponseDTO> findByClient(Long clientId);
    void cancelar(Long id);
    ProductResponseDTO update(Long id, ProductRequestDTO dto);
}
