package ru.obvilion.mindHelper.response;

import ru.obvilion.mindHelper.model.map.MapType;

public class MapsTypeCountResponse {
    private MapType type;
    private int count;

    public MapsTypeCountResponse(MapType type, int count) {
        this.type = type;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MapType getType() {
        return type;
    }

    public void setType(MapType type) {
        this.type = type;
    }
}
