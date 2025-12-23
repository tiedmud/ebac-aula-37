package br.com.ebac.ebac_modulo_51.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UsuarioRequestDTO {
    @NotNull(message="O nome não pode ser nulo.")
    private String nome;

    @Min(value=0, message="Idade deve ser maior que 0.")
    @Max(value=150, message="Idade deve ter ser menor que 150.")
    private Integer idade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
