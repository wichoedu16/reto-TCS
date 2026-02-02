package com.banco.cuenta.service.impl;

import com.banco.cuenta.dto.CuentaDTO;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.exception.RecursoDuplicadoException;
import com.banco.cuenta.exception.RecursoNoEncontradoException;
import com.banco.cuenta.mapper.CuentaMapper;
import com.banco.cuenta.repository.CuentaRepository;
import com.banco.cuenta.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    @Override
    public CuentaDTO crear(CuentaDTO dto) {
        if (cuentaRepository.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new RecursoDuplicadoException("Cuenta", "numeroCuenta", dto.getNumeroCuenta());
        }
        Cuenta entity = cuentaMapper.dtoToEntity(dto);
        return cuentaMapper.entityToDto(cuentaRepository.save(entity));
    }

    @Override
    public CuentaDTO actualizar(Long id, CuentaDTO dto) {
        Cuenta entity = cuentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta", id));

        // Si cambian el numeroCuenta, valida duplicado (evita pisar unique)
        if (!entity.getNumeroCuenta().equals(dto.getNumeroCuenta())
                && cuentaRepository.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new RecursoDuplicadoException("Cuenta", "numeroCuenta", dto.getNumeroCuenta());
        }

        entity.setNumeroCuenta(dto.getNumeroCuenta());
        entity.setTipoCuenta(dto.getTipoCuenta());
        entity.setSaldoInicial(dto.getSaldoInicial());
        // saldoActual NO se pisa aquí (lo maneja movimientos)
        entity.setEstado(dto.getEstado());
        entity.setClienteId(dto.getClienteId());

        return cuentaMapper.entityToDto(cuentaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaDTO obtenerPorId(Long id) {
        Cuenta entity = cuentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta", id));
        return cuentaMapper.entityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaDTO obtenerPorNumero(String numeroCuenta) {
        Cuenta entity = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta", "numeroCuenta", numeroCuenta));
        return cuentaMapper.entityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> listar() {
        return cuentaMapper.entitiesToDtos(cuentaRepository.findAll());
    }

    @Override
    public void eliminar(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Cuenta", id);
        }
        cuentaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> listarPorCliente(String clienteId) {
        return cuentaMapper.entitiesToDtos(cuentaRepository.findByClienteId(clienteId));
    }
}
