package com.example.Sistema.Colaborador.specification;

import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Colaborador.model.Colaborador;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;

public interface ColaboradorSpecification {

     static Specification<Colaborador> FiltroColaborador(FilterColaborador filter) {
        Specification<Colaborador> specification = filterByProperty("nome",filter.getNome())
        .and(filterByProperty("sobrenome",filter.getSobrenome()))
        .and(filterByProperty("cpf",filter.getCpf()))
        .and(filterByProperty("estado",filter.getEstado()))
        .and(filterByProperty("cidade", filter.getCidade()))
        .and(filterByPropertyInterger("ativo", filter.getAtivo()));
     return specification;
    }

    private static Specification<Colaborador> filterByProperty(String property, String... values) {
        if (values == null || Arrays.stream(values).toList().contains(null)) {
            return (root, query, builder) -> builder.conjunction();
        }
        return (root, query, builder) -> builder.or(Arrays
                .stream(values)
                .map(value -> builder.like(root.get(property), "%" + value + "%"))
                .toArray(Predicate[]::new));
    }

    private static Specification<Colaborador> filterByPropertyInterger(String property, Integer... values) {
        if (values == null || Arrays.stream(values).toList().contains(null)) {
            return (root, query, builder) -> builder.conjunction();
        }
        return (root, query, builder) -> builder.or(Arrays
                .stream(values)
                .map(value -> builder.equal(root.get(property), value ))
                .toArray(Predicate[]::new));
    }

}
