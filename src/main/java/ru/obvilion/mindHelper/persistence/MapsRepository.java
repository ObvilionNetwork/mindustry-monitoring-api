package ru.obvilion.mindHelper.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.obvilion.mindHelper.model.map.MapModel;
import ru.obvilion.mindHelper.model.map.MapType;

import java.util.List;

@Repository
public interface MapsRepository extends MongoRepository<MapModel, String> {
    long countByType(MapType type);

    List<MapModel> findAllById(int id);

    List<MapModel> findAllBySize(int size);

    List<MapModel> findAllByName(String name);

    List<MapModel> findAllByAuthor(String author);
}
