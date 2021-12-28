package com.health.my_heart.utils;

public interface Mapper<Domain, Entity> {
    public Domain toDomain(Entity entity);
    public Entity toEntity(Domain domain);
}
