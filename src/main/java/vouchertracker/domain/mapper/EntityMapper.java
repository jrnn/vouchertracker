package vouchertracker.domain.mapper;

public interface EntityMapper<Entity, Dto> {

    Entity mapDtoToEntity(Dto dto, Entity entity, String user);

    Dto mapEntityToDto(Dto dto, Entity entity);

    String writeToCsv(String separator);

}