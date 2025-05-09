package org.acme.dto;

import java.math.BigDecimal;

public record StudentData(
                Long id,
                String name,
                BigDecimal balance) {
}