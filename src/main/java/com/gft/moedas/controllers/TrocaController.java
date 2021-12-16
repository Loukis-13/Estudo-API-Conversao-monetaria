package com.gft.moedas.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gft.moedas.DTO.troca.DataTrocaDTO;
import com.gft.moedas.DTO.troca.FazerTrocaDTO;
import com.gft.moedas.DTO.usuario.ConsultaUsuarioDTO;
import com.gft.moedas.entities.Troca;
import com.gft.moedas.entities.Usuario;
import com.gft.moedas.exception.TrocaInvalidaException;
import com.gft.moedas.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("troca")
@Tag(name = "Troca", description = "trocas entre moedas")
@SecurityRequirement(name = "bearerAuth")
public class TrocaController {
    @Autowired
    UsuarioService usuarioService;

    public static Map<String, Double> moedas;
    public static Date tempoMoedas = new Date(new Date().getTime() - 86400000);


    public static Map<String, Double> valoresMoedas() {
        Map<String, Map<String, Double>> resp = new RestTemplate().getForObject("https://www.currency-api.com/rates?base=BRL", Map.class);
        return resp.get("rates");
    }

    @GetMapping
    @Operation(summary = "Ver valores de todas as moedas disponíveis para troca, com base no Real")
    public ResponseEntity valorMoedas() {
        if ((new Date().getTime() - 86400000) > tempoMoedas.getTime()) {
            moedas = valoresMoedas();
            tempoMoedas = new Date();
        }

        return ResponseEntity.ok().body(moedas);
    }

    @PostMapping
    @Operation(summary = "Troca entre duas moedas")
    public ResponseEntity TrocarMoedas(@RequestBody FazerTrocaDTO dto, Authentication authentication) {
        if ((new Date().getTime() - 86400000) > tempoMoedas.getTime()) {
            moedas = valoresMoedas();
            tempoMoedas = new Date();
        }

        List<String> erros = new ArrayList<>();
        if (moedas.get(dto.getMoeda1()) == null) erros.add("Moeda 1 invalida");
        if (moedas.get(dto.getMoeda2()) == null) erros.add("Moeda 2 invalida");
        if (dto.getQuantidade() <= 0) erros.add("Quantidade não pode ser menor ou igual a zero");
        if (!erros.isEmpty()) throw new TrocaInvalidaException(erros);

        Troca troca = new Troca(
                dto.getMoeda1(),
                dto.getMoeda2(),
                dto.getQuantidade(),
                dto.getQuantidade() / (moedas.get(dto.getMoeda1()) / moedas.get(dto.getMoeda2())),
                moedas.get(dto.getMoeda2()) / moedas.get(dto.getMoeda1()),
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())
        );

        Usuario usuario = usuarioService.buscarUsuarioPorNome(authentication.getName());
        usuario.getTrocas().add(0, troca);
        usuarioService.salvarUsuario(usuario);

        return ResponseEntity.ok(troca);
    }

    @DeleteMapping
    @Operation(summary = "Apagar um registro de troca pela data em foi feita")
    public ResponseEntity apagarTroca(@RequestBody DataTrocaDTO data, Authentication authentication) {
        Usuario usuario = usuarioService.buscarUsuarioPorNome(authentication.getName());

        usuario.setTrocas(usuario.getTrocas().stream().filter(i -> !i.getFeitaEm().equals(data.data)).collect(Collectors.toList()));

        usuarioService.salvarUsuario(usuario);

        return ResponseEntity.ok(new ConsultaUsuarioDTO(usuario));
    }
}
