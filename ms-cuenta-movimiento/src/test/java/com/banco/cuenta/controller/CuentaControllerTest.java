package com.banco.cuenta.controller;

import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.repository.CuentaRepository;
import com.banco.cuenta.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CuentaController Integration Tests")
class CuentaControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private CuentaRepository cuentaRepository;
    @Autowired private MovimientoRepository movimientoRepository;

    @BeforeEach
    void setUp() {
        // Por FK: movimientos primero
        movimientoRepository.deleteAll();
        cuentaRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /cuentas - Crear cuenta exitoso")
    void crearCuentaExitoso() throws Exception {
        String json = """
                {
                  "numeroCuenta": "225487",
                  "tipoCuenta": "Corriente",
                  "saldoInicial": 100,
                  "estado": true,
                  "clienteId": "CLI001"
                }
                """;

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        Optional<Cuenta> guardada = cuentaRepository.findByNumeroCuenta("225487");
        assertThat(guardada).isPresent();
        assertThat(guardada.get().getSaldoActual()).isEqualByComparingTo(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("GET /cuentas - Listar todas")
    void listarTodas() throws Exception {
        mockMvc.perform(get("/cuentas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /cuentas/{id} - Cuenta no encontrada")
    void buscarPorIdNoEncontrado() throws Exception {
        mockMvc.perform(get("/cuentas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /cuentas/{id} - Cuenta no encontrada")
    void eliminarNoEncontrado() throws Exception {
        mockMvc.perform(delete("/cuentas/999"))
                .andExpect(status().isNotFound());
    }
}
