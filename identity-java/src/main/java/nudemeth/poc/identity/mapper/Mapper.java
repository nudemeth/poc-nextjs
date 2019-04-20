package nudemeth.poc.identity.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<M, E> {

    default List<E> mapToEntity(final List<M> list) {
        return list.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    default List<M> mapToModel(final List<E> list) {
        return list.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    E convertToEntity(final M model);
    M convertToModel(final E entity);
}