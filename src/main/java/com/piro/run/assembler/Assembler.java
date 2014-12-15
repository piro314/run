package com.piro.run.assembler;

import java.util.List;

/**
 * Created by ppirovski on 12/15/14. In Code we trust
 */
public interface Assembler<E,D> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntities(List<D> dtos);

    List<D> toDtos(List<E> entities);
}
