package ru.obvilion.mindHelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.obvilion.mindHelper.model.map.MapModel;
import ru.obvilion.mindHelper.model.map.MapType;
import ru.obvilion.mindHelper.persistence.MapsRepository;

import java.util.List;

@Service
public class MapsService {
    @Autowired
    private MapsRepository repository;

    // Find functions
    public List<MapModel> findAll() {
        return repository.findAll();
    }

    public List<MapModel> findAllOnPage(int page) {
        Pageable pageable = PageRequest.of(page - 1, 50);

        return repository.findAll(pageable).getContent();
    }

    public List<MapModel> findAllBySize(int size) {
        return repository.findAllBySize(size);
    }

    public List<MapModel> findAllByName(String name) {
        return repository.findAllByName(name);
    }

    // Getter functions
    public MapModel getById(int id) {
        List<MapModel> maps = repository.findAllById(id);

        return maps.size() > 0 ? maps.get(0) : null;
    }

    // Count getter functions
    public int getCount() {
        return (int) repository.count();
    }

    public int getCount(MapType type) {
        return (int) repository.countByType(type);
    }

    // Add functions
    public void add(MapModel map) {
        map.setId((int) repository.count());
        repository.insert(map);
    }
}
