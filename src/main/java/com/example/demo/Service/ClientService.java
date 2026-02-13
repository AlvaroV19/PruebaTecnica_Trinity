package com.example.demo.Service;

import com.example.demo.Entity.Client;
import com.example.demo.IService.IClientService;
import com.example.demo.Repository.ClientRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productoRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client save(Client cliente) {

        int edad = Period.between(cliente.getFechaNacimiento(), LocalDate.now()).getYears();

        if (edad < 18) {
            throw new RuntimeException("Cliente menor de edad");
        }

        cliente.setFechaCreacion(LocalDateTime.now());
        return clientRepository.save(cliente);
    }

    @Override
    public void update(Client cliente, Long id) {

        Optional<Client> c = clientRepository.findById(id);

        if (c.isPresent()) {
            Client clientUpdate = c.get();
            clientUpdate.setNombres(cliente.getNombres());
            clientUpdate.setApellidos(cliente.getApellidos());
            clientUpdate.setCorreoElectronico(cliente.getCorreoElectronico());
            clientRepository.save(clientUpdate);
        }
    }

    @Override
    public void delete(Long id) {

        if (!productoRepository.findByClient_Id(id).isEmpty()) {
            throw new RuntimeException("No se puede eliminar cliente con productos activos");
        }

        clientRepository.deleteById(id);
    }
}
