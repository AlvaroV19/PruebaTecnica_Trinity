package com.example.demo.Service;

import com.example.demo.DTO.ClientRequestDTO;
import com.example.demo.DTO.ClientResponseDTO;
import com.example.demo.DTO.ProductResponseDTO;
import com.example.demo.Entity.Client;
import com.example.demo.Entity.Product;
import com.example.demo.IService.IClientService;
import com.example.demo.Repository.ClientRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productoRepository;

    @Override
    public List<ClientResponseDTO> findAll() {

        List<Client> clients = clientRepository.findAll();
        List<ClientResponseDTO> responseList = new ArrayList<>();

        for (Client client : clients) {

            ClientResponseDTO response = new ClientResponseDTO();
            response.setId(client.getId());
            response.setNombres(client.getNombres());
            response.setApellido(client.getApellido());
            response.setEmail(client.getEmail());
            response.setTipoIdentificacion(client.getTipoIdentificacion());
            response.setNumeroIdentificacion(client.getNumeroIdentificacion());
            response.setFechaNacimiento(client.getFechaNacimiento());
            response.setFechaCreacion(client.getFechaCreacion());
            response.setFechaModificacion(client.getFechaModificacion());

            List<ProductResponseDTO> productosDTO = new ArrayList<>();

            for (Product product : client.getProducts()) {

                ProductResponseDTO productDTO = new ProductResponseDTO();
                productDTO.setId(product.getId());
                productDTO.setTipoCuenta(product.getTipoCuenta());
                productDTO.setSaldo(product.getSaldo());
                productDTO.setClientId(client.getId());
                productDTO.setClientNombre(client.getNombres());

                productosDTO.add(productDTO);
            }

            response.setProductos(productosDTO);
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public ClientResponseDTO findById(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(client.getId());
        response.setNombres(client.getNombres());
        response.setApellido(client.getApellido());
        response.setEmail(client.getEmail());
        response.setTipoIdentificacion(client.getTipoIdentificacion());
        response.setNumeroIdentificacion(client.getNumeroIdentificacion());
        response.setFechaNacimiento(client.getFechaNacimiento());
        response.setFechaCreacion(client.getFechaCreacion());
        response.setFechaModificacion(client.getFechaModificacion());

        List<ProductResponseDTO> productosDTO = new ArrayList<>();

        for (Product product : client.getProducts()) {

            ProductResponseDTO productDTO = new ProductResponseDTO();
            productDTO.setId(product.getId());
            productDTO.setTipoCuenta(product.getTipoCuenta());
            productDTO.setSaldo(product.getSaldo());
            productDTO.setClientId(client.getId());
            productDTO.setClientNombre(client.getNombres());

            productosDTO.add(productDTO);
        }

        response.setProductos(productosDTO);

        return response;
    }

    @Override
    public ClientResponseDTO save(ClientRequestDTO dto) {

        if (clientRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Client client = new Client();
        client.setTipoIdentificacion(dto.getTipoIdentificacion());
        client.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        client.setNombres(dto.getNombres());
        client.setApellido(dto.getApellido());
        client.setEmail(dto.getEmail());
        client.setFechaNacimiento(dto.getFechaNacimiento());

        client.setFechaCreacion(LocalDateTime.now());
        client.setFechaModificacion(LocalDateTime.now());

        validarMayorEdad(dto.getFechaNacimiento());
        validarNombreApellido(dto.getNombres(), "Nombres");
        validarNombreApellido(dto.getApellido(), "Apellido");
        validarEmail(dto.getEmail());

        Client saved = clientRepository.save(client);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(saved.getId());
        response.setTipoIdentificacion(saved.getTipoIdentificacion());
        response.setNumeroIdentificacion(saved.getNumeroIdentificacion());
        response.setNombres(saved.getNombres());
        response.setApellido(saved.getApellido());
        response.setEmail(saved.getEmail());
        response.setFechaNacimiento(saved.getFechaNacimiento());
        response.setFechaCreacion(saved.getFechaCreacion());
        response.setFechaModificacion(saved.getFechaModificacion());

        return response;
    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!client.getEmail().equals(dto.getEmail()) &&
                clientRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        client.setNombres(dto.getNombres());
        client.setApellido(dto.getApellido());
        client.setTipoIdentificacion(dto.getTipoIdentificacion());
        client.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        client.setEmail(dto.getEmail());
        client.setFechaNacimiento(dto.getFechaNacimiento());
        client.setFechaModificacion(LocalDateTime.now());

        validarMayorEdad(dto.getFechaNacimiento());
        validarNombreApellido(dto.getNombres(), "Nombres");
        validarNombreApellido(dto.getApellido(), "Apellido");
        validarEmail(dto.getEmail());

        Client updated = clientRepository.save(client);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(updated.getId());
        response.setNombres(updated.getNombres());
        response.setApellido(updated.getApellido());
        response.setEmail(updated.getEmail());
        response.setTipoIdentificacion(updated.getTipoIdentificacion());
        response.setNumeroIdentificacion(updated.getNumeroIdentificacion());
        response.setFechaNacimiento(updated.getFechaNacimiento());


        return response;
    }

    @Override
    public void delete(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (client.getProducts() != null && !client.getProducts().isEmpty()) {
            throw new RuntimeException("No se puede eliminar cliente con productos activos");
        }

        clientRepository.deleteById(id);
    }

    private void validarMayorEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new RuntimeException("La fecha de nacimiento es obligatoria");
        }

        int edad = LocalDate.now().getYear() - fechaNacimiento.getYear();

        if (edad < 18) {
            throw new RuntimeException("El cliente debe ser mayor de edad");
        }
    }

    private void validarEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException("Formato de email inválido");
        }
    }

    private void validarNombreApellido(String texto, String campo) {
        if (texto == null || texto.trim().length() < 2) {
            throw new RuntimeException(campo + " debe tener mínimo 2 caracteres");
        }
    }
}
