# PruebaTecnica_Trinity

 1. Descripción 

    Aplicación backend desarrollada en Java (Spring Boot) como prueba técnica para una entidad financiera.
    El sistema permite la administración de clientes, productos financieros (cuentas) y transacciones, 
    exponiendo una API REST que cumple con reglas de negocio específicas del sector financiero.
    
 2. Funcionalidades Principales
   
    - 👤 Clientes:
    
        - CRUD completo de clientes.
        
        - Validación de mayoría de edad.
        
        - No se permite eliminar clientes con productos asociados.
        
        - Fechas de creación y modificación automáticas.
        
        - Validación de formato de correo electrónico.
    
    - 💳 Productos (Cuentas)
    
        - Creación de cuentas de ahorro y corriente.
        
        - Número de cuenta único y autogenerado (10 dígitos).
        
        - Prefijo:
        
        - Ahorros → 53
        
        - Corriente → 33
        
        - Restricción de saldo mínimo en cuentas de ahorro.
        
        - Cancelación permitida solo con saldo en $0.
        
        - Actualización automática de saldo tras transacciones.
    
    - 💰 Transacciones
    
        - Consignación.
        
        - Retiro.
        
        - Transferencia entre cuentas existentes.
        
        - Actualización automática de saldos.
        
        - Generación de movimientos débito/crédito en transferencias.


 3. Arquitectura

    Proyecto estructurado por capas:
    
    controller → service → repository → entity

    Implementa patrón DTO para separar los modelos de dominio de los contratos REST.


 4. Tecnologías

    - Java
    
    - Spring Boot
    
    - JPA / Hibernate
    
    - Base de datos relacional (Postgres)
    
    - JUnit
    
    - Git & GitHub
