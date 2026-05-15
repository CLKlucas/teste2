package br.com.topcar.service.interfaces.strategy;

import java.math.BigDecimal;

public interface iPromoStrategy {
    BigDecimal aplicarDesconto(BigDecimal valor);
}
