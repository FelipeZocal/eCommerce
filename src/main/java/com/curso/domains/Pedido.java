package com.curso.domains;

import com.curso.domains.dtos.PedidoDTO;
import com.curso.freteStrategy.FreteAereo;
import com.curso.freteStrategy.FreteStrategy;
import com.curso.freteStrategy.FreteTerrestre;
import com.curso.state.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pedido")
public class Pedido{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPedido;

    @Column(nullable = false, unique = true, updatable = false)
    private Long numPedido;

    @NotNull @NotBlank
    private String cliente;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal valorPedido;

    private BigDecimal valorFrete;

    private BigDecimal valorTotal;

    @JsonIgnore
    @Transient
    private FreteStrategy frete;

    @NotNull
    private String modoFrete;

    @Transient
    private PedidoState state;

    private String stateAtual;

    public void sucessoAoPagar() {
        if (state == null) this.state = createStateFromString(this.stateAtual);
        state.sucessoAoPagar();
    }

    public void cancelarPedido() {
        if (state == null) this.state = createStateFromString(this.stateAtual);
        state.cancelarPedido();
    }

    public void enviaPedido() {
        if (state == null) this.state = createStateFromString(this.stateAtual);
        state.enviaPedido();
    }

    private FreteStrategy createFreteFromString(String tipo) {
        return switch (tipo) {
            case "Aereo" -> new FreteAereo();
            case "Terrestre" -> new FreteTerrestre();
            default -> throw new IllegalArgumentException("Tipo de frete não existente: " + tipo);
        };
    }

    private PedidoState createStateFromString(String state) {
        return switch (state) {
            case "Aguardando pagamento" -> new AguardandoPagamentoState(this);
            case "Pagamento realizado com sucesso" -> new PagoState(this);
            case "Pedido enviado" -> new EnviadoState(this);
            case "Pedido cancelado" -> new CanceladoState(this);
            default -> throw new IllegalArgumentException("Estado inválido do pedido: " + state);
        };
    }

    public Pedido() { }

    public Pedido(UUID idPedido, String cliente, BigDecimal valorPedido, FreteStrategy frete) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.valorPedido = valorPedido;
        this.frete = frete;
        if(frete != null){
            this.modoFrete = frete.getFrete();
        }

        if(state != null){
            this.setStateAtual(stateAtual);
        }
    }


    public Pedido(PedidoDTO dto){

    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public Long getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Long numPedido) {
        this.numPedido = numPedido;
    }

    public @NotNull @NotBlank String getCliente() {
        return cliente;
    }

    public void setCliente(@NotNull @NotBlank String cliente) {
        this.cliente = cliente;
    }

    public @NotNull @Digits(integer = 10, fraction = 2) BigDecimal getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(@NotNull @Digits(integer = 10, fraction = 2) BigDecimal valorPedido) {
        this.valorPedido = valorPedido;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public FreteStrategy getFrete() {
        if(frete == null && modoFrete != null){
            if("Aereo".equals(modoFrete)){
                frete = new FreteAereo();
            } else if("Terreste".equals(modoFrete)){
                frete = new FreteTerrestre();
            }
        }
        return frete;
    }

    public void setFrete(FreteStrategy frete) {
        this.frete = frete;
        if (frete != null) {
            this.modoFrete = frete.getFrete();
        }
    }

    public @NotNull String getModoFrete() {
        return modoFrete;
    }

    public void setModoFrete(@NotNull String modoFrete) {
        this.modoFrete = modoFrete;
    }

    public @NotNull PedidoState getState() {
        if("Aguardando pagamento".equals(stateAtual)){
            return new AguardandoPagamentoState(this);
        } else if("Pagamento realizado com sucesso".equals(stateAtual)){
            return new PagoState(this);
        } else if("Pedido enviado".equals(stateAtual)){
            return new EnviadoState(this);
        } else if("Pedido cancelado".equals(stateAtual)){
            return new CanceladoState(this);
        }
        return null;
    }

    public void setState(@NotNull PedidoState state) {
        this.stateAtual = state.getState();
    }

    public String getStateAtual() {
        return stateAtual;
    }

    public void setStateAtual(String stateAtual) {
        this.stateAtual = stateAtual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(idPedido, pedido.idPedido) && Objects.equals(numPedido, pedido.numPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, numPedido);
    }
}