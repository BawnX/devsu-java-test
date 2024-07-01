package com.hexagonal.client.infrastructure.dtos;

public class ClienteDto {
    private final String ClienteId;
    private final String PersonaId;
    private final String Nombre;
    private final String Genero;
    private final String Edad;
    private final String Direccion;
    private final String Telefono;
    private final String Contraseña;
    private final boolean Estado;

    public ClienteDto(String ClienteId,
            String PersonaId,
            String Contraseña,
            String Direccion,
            String Edad,
            boolean Estado,
            String Genero,
            String Nombre,
            String Telefono) {
        this.ClienteId = ClienteId;
        this.Contraseña = Contraseña;
        this.Direccion = Direccion;
        this.Edad = Edad;
        this.Estado = Estado;
        this.Genero = Genero;
        this.Nombre = Nombre;
        this.PersonaId = PersonaId;
        this.Telefono = Telefono;
    }

    public String getClienteId() {
        return ClienteId;
    }

    public String getPersonaId() {
        return PersonaId;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getGenero() {
        return Genero;
    }

    public String getEdad() {
        return Edad;
    }

    public String getDireccion() {
        return Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public boolean getEstado() {
        return Estado;
    }
}
