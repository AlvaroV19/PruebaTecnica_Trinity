package com.example.demo.IService;

import com.example.demo.Entity.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    List<Client> findAll();
    Optional<Client> findById(Long id);
    Client save(Client client);
    void update(Client client, Long id);
    void delete(Long id);
}
