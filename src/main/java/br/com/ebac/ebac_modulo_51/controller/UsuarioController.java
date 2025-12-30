package br.com.ebac.ebac_modulo_51.controller;

import br.com.ebac.ebac_modulo_51.dto.UsuarioRequestDTO;
import br.com.ebac.ebac_modulo_51.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("api/v1/usuario")
@Tag(name="Usuarios", description="Operações relacionadas a Usuários")
public class UsuarioController {
    private final Map<UUID, Usuario> usuarios = new HashMap<>();

    private final JwtEncoder jwtEncoder;

    public UsuarioController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/login")
    @Operation(summary="Listar usuários", description="Retornar todos os itens da lista.")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username) {
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("ebac-modulo-51").issuedAt(Instant.now()).expiresAt(Instant.now()
                .plusSeconds(3600)).subject(username).build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping
    @Operation(summary="Listar usuários", description="Retornar todos os itens da lista.")
        public Collection<Usuario> listarTodos() {
            return usuarios.values();
    }

    @PostMapping
    @Operation(summary="Listar usuários", description="Retornar todos os itens da lista.")
    public Usuario criarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = new Usuario(UUID.randomUUID(), usuarioDTO.getNome(), usuarioDTO.getIdade());
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @PatchMapping("/{id}")
    @Operation(summary="Listar usuários", description="Retornar todos os itens da lista.")
    public Usuario atualizarUsuario(@PathVariable String id, @Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = usuarios.get(UUID.fromString(id));

        if (usuario == null) {
            throw new RuntimeException("Item não encontrado.");
        } else {
            usuario.setIdade(usuarioDTO.getIdade());
            usuario.setNome(usuarioDTO.getNome());

            return usuario;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Listar usuários", description="Retornar todos os itens da lista.")
    public String deletarUsuario(@PathVariable String id) {
        Usuario usuario = usuarios.remove(id);

        if (usuario == null) {
            return "Usuário não existe.";
        } else {
            return "Usuário removido com sucesso.";
        }
    }
}