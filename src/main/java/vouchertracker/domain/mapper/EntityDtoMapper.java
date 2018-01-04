package vouchertracker.domain.mapper;

public interface EntityDtoMapper<Entity, Dto> {

    Entity mapDtoToEntity(Dto dto, Entity entity, String user);

    Dto mapEntityToDto(Dto dto, Entity entity);

}