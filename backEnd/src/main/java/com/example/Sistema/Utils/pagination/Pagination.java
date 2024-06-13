package com.example.Sistema.Utils.pagination;

import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Utils.filtro.Filtro;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.Objects;

public interface Pagination {
    static Pageable createPageableFromFiltro(FilterColaborador filtro, Map<String, String> CAMPO_MAP, String OrderInitial) {
        if (Objects.isNull(filtro.getId())) {
            filtro.setId(OrderInitial);
            filtro.setDesc(true);
        }

        String campoInterno = CAMPO_MAP.getOrDefault(filtro.getId(), filtro.getId());
        Sort sort = filtro.isDesc() ? Sort.by(campoInterno).descending() : Sort.by(campoInterno).ascending();
        return PageRequest.of(filtro.getPagina(), filtro.getTamanhoPagina(), sort);
    }
}
