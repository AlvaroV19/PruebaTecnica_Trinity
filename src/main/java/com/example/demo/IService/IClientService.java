package com.example.demo.IService;

import com.example.demo.DTO.ClientRequestDTO;
import com.example.demo.DTO.ClientResponseDTO;

import java.util.List;


public interface IClientService {

    List<ClientResponseDTO> findAll();
    ClientResponseDTO findById(Long id);
    ClientResponseDTO save(ClientRequestDTO dto);
    ClientResponseDTO update(Long id, ClientRequestDTO dto);
    void delete(Long id);
}
