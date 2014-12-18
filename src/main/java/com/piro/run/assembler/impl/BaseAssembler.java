package com.piro.run.assembler.impl;

import com.piro.run.assembler.Assembler;
import org.apache.commons.collections.ArrayStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ppirovski on 12/15/14. In Code we trust
 */
public abstract class BaseAssembler<E,D> implements Assembler<E,D> {

    @Override
    public List<E> toEntities(List<D> dtos) {
        List<E> result = new ArrayList<>();
        if(dtos != null && !dtos.isEmpty()) {
            for (D dto : dtos) {
                E entity = toEntity(dto);
                result.add(entity);
            }
        }

        return result;
    }

    @Override
    public List<D> toDtos(List<E> entities) {
        List<D> result = new ArrayList<>();

        if(entities != null && !entities.isEmpty()) {
            for (E entity : entities) {
                D dto = toDto(entity);
                result.add(dto);
            }
        }
        return result;
    }
}
