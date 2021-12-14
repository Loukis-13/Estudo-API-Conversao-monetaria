package com.gft.moedas.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gft.moedas.DTO.troca.DataTrocaDTO;
import com.gft.moedas.DTO.troca.FazerTrocaDTO;
import com.gft.moedas.DTO.usuario.ConsultaUsuarioDTO;
import com.gft.moedas.entities.Troca;
import com.gft.moedas.entities.Usuario;
import com.gft.moedas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("troca")
public class TrocaController {
    @Autowired
    UsuarioService usuarioService;

    public static Map<String, Double> moedas;
    public static Date tempoMoedas = new Date(new Date().getTime() - 86400000);


    public static Map<String, Double> valoresMoedas() throws JsonProcessingException {
        Map<String, Map<String, Double>> resp = new RestTemplate().getForObject("https://www.currency-api.com/rates?base=BRL", Map.class);
        return resp.get("rates");
    }

    @GetMapping
    public ResponseEntity valorMoedas() {
        try {
            if ((new Date().getTime() - 86400000) > tempoMoedas.getTime()) {
                moedas = valoresMoedas();
                tempoMoedas = new Date();
            }

            return ResponseEntity.ok().body(moedas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity TrocarMoedas(@RequestBody FazerTrocaDTO dto, Authentication authentication) {
        try {
            if ((new Date().getTime() - 86400000) > tempoMoedas.getTime()) {
                moedas = valoresMoedas();
                tempoMoedas = new Date();
            }

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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity apagarTroca(@RequestBody DataTrocaDTO data, Authentication authentication) {
        Usuario usuario = usuarioService.buscarUsuarioPorNome(authentication.getName());

        usuario.setTrocas(usuario.getTrocas().stream().filter(i -> !i.getFeitaEm().equals(data.data)).collect(Collectors.toList()));

        usuarioService.salvarUsuario(usuario);

        return ResponseEntity.ok(new ConsultaUsuarioDTO(usuario));
    }
}
