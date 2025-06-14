package com.curso.freteStrategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FreteTerrestre implements FreteStrategy{

    @Override
    public BigDecimal calcular(BigDecimal valorPedido){
        return valorPedido.multiply(new BigDecimal("0.05"));
    }

    @Override
    public String getFrete(){
        return "Terreste";
    }
}
