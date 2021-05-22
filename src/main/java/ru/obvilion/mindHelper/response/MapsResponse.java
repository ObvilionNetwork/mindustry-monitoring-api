package ru.obvilion.mindHelper.response;

import java.util.List;

public class MapsResponse {
    private int count;
    private List<MapsTypeCountResponse> types;

    public MapsResponse(int count, List<MapsTypeCountResponse> types) {
        this.count = count;
        this.types = types;
    }

    public List<MapsTypeCountResponse> getTypes() {
        return types;
    }

    public void setTypes(List<MapsTypeCountResponse> types) {
        this.types = types;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

