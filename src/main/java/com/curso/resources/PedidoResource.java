package com.curso.resources;

import com.curso.domains.Pedido;
import com.curso.domains.dtos.PedidoDTO;
import com.curso.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> findById(@PathVariable UUID idPedido) {
        Pedido pedido = pedidoService.findById(idPedido);
        return ResponseEntity.ok(new PedidoDTO(pedido));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> create(@Valid @RequestBody PedidoDTO dto) {
        Pedido pedido = pedidoService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idPedido}").buildAndExpand(pedido.getIdPedido()).toUri();
        return ResponseEntity.created(uri).body(new PedidoDTO(pedido));
    }

    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> update(@PathVariable UUID idPedido, @Valid @RequestBody PedidoDTO dto) {
        Pedido atualizado = pedidoService.update(idPedido, dto);
        return ResponseEntity.ok(new PedidoDTO(atualizado));
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> delete(@PathVariable UUID idPedido) {
        pedidoService.delete(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idPedido}/pagar")
    public ResponseEntity<PedidoDTO> pagarPedido(@PathVariable UUID idPedido) {
        Pedido pedido = pedidoService.pagarPedido(idPedido);
        return ResponseEntity.ok(new PedidoDTO(pedido));
    }

    @PatchMapping("/{idPedido}/cancelar")
    public ResponseEntity<PedidoDTO> cancelaPedido(@PathVariable UUID idPedido) {
        Pedido pedido = pedidoService.cancelaPedido(idPedido);
        return ResponseEntity.ok(new PedidoDTO(pedido));
    }

    @PatchMapping("/{idPedido}/despachar")
    public ResponseEntity<PedidoDTO> enviaPedidoPedido(@PathVariable UUID idPedido) {
        Pedido pedido = pedidoService.enviaPedido(idPedido);
        return ResponseEntity.ok(new PedidoDTO(pedido));
    }
}
