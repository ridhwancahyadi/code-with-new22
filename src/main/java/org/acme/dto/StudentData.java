package org.acme.dto;

import java.math.BigDecimal;
import java.util.Date;

public record StudentData(
                // Long id,
                String name,
                String jurusan,
                Float ipk,
                Date tanggalLahir,
                BigDecimal balance) {
}