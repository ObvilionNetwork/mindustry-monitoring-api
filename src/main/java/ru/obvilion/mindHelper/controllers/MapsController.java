package ru.obvilion.mindHelper.controllers;

import arc.files.Fi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.obvilion.mindHelper.ContentHandler;
import ru.obvilion.mindHelper.MindHelperApplication;
import ru.obvilion.mindHelper.model.map.MapModel;
import ru.obvilion.mindHelper.model.map.MapType;
import ru.obvilion.mindHelper.response.ErrorResponse;
import ru.obvilion.mindHelper.response.MapsResponse;
import ru.obvilion.mindHelper.response.MapsTypeCountResponse;
import ru.obvilion.mindHelper.service.MapsService;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("maps")
public class MapsController {
    @Autowired
    private MapsService service;

    private ContentHandler handler = new ContentHandler();

    @GetMapping
    public Object list(@RequestParam(value = "page", required = false) String page) {
        if (page != null) {
            try {
                final int findPage = Integer.parseInt(page);
                return service.findAllOnPage(findPage);
            } catch (Exception e) {
                // Ignored
            }
        }

        List<MapsTypeCountResponse> types = new ArrayList<>();

        for (MapType type : MapType.values()) {
            types.add(new MapsTypeCountResponse(type, service.getCount(type)));
        }

        return new MapsResponse(service.getCount(), types);
    }

    @GetMapping("{id}")
    public Object find(@PathVariable String id) {
        try {
            final int findId = Integer.parseInt(id);
            final MapModel map = service.getById(findId);

            return map == null
                    ? new ErrorResponse(1001, "Index out of bounds")
                    : map;
        } catch (Exception e) {
            return new ErrorResponse(1000, e.getLocalizedMessage());
        }
    }

    @GetMapping("{id}/preview")
    public Object getPreview(@PathVariable String id) {
        try {
            final int findId = Integer.parseInt(id);
            final MapModel map = service.getById(findId);

            if (map == null) {
                return new ErrorResponse(1001, "Index out of bounds");
            }

            Fi newMapFile = new Fi(
                new File(MindHelperApplication.mapsFolder, map.getId() + ".msav")
            );

            ContentHandler.Map mapContent = handler.readMap(newMapFile.read());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(mapContent.image, "png", byteArrayOutputStream);

            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"image.png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            return new ErrorResponse(1000, e.getLocalizedMessage());
        }
    }

    @GetMapping("{id}/download")
    public Object getFile(@PathVariable String id) {
        try {
            final int findId = Integer.parseInt(id);
            final MapModel map = service.getById(findId);

            if (map == null) {
                return new ErrorResponse(1001, "Index out of bounds");
            }

            Fi newMapFile = new Fi(
                    new File(MindHelperApplication.mapsFolder, map.getId() + ".msav")
            );

            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + map.getName().replaceAll(" ", "_") + ".msav\"")
                    .contentType(MediaType.MULTIPART_MIXED)
                    .body(newMapFile.readBytes());
        } catch (Exception e) {
            return new ErrorResponse(1000, e.getLocalizedMessage());
        }
    }

    @PostMapping
    public Object addMap(@RequestParam MultipartFile file, @RequestParam String mapType, @RequestHeader String token, ModelMap modelMap) {
        if (file == null) {
            return new ErrorResponse(7000, "File not found");
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".msav")) {
            return new ErrorResponse(7001, "File extension is invalid");
        }

        MapType type;
        try {
            type = MapType.valueOf(mapType);
        } catch (Exception e) {
            return new ErrorResponse(8002, "Map type is invalid");
        }

        Fi newMapFile = new Fi(
            new File(MindHelperApplication.mapsFolder, service.getCount() + ".msav")
        );

        try {
            file.transferTo(newMapFile.file());
        } catch (Exception e) {
            return new ErrorResponse(1000, e.getLocalizedMessage());
        }

        ContentHandler.Map map;
        try {
            map = handler.readMap(newMapFile.read());
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(8001, "Error parsing map");
        }

        List<MapModel> maps = service.findAllBySize((int) file.getSize());
        for (MapModel mapModel : maps) {
            if (mapModel.getName().equals(map.name)) {
                return new ErrorResponse(8080, "Map exists");
            }
        }

        MapModel model = new MapModel(0, map.name, map.author, map.description, map.tags.get("build"),
                Integer.parseInt(map.tags.get("width")), Integer.parseInt(map.tags.get("height")),
                Instant.ofEpochMilli(Long.parseLong(map.tags.get("saved"))), type, (int) file.getSize());

        service.add(model);

        return model;
    }
}
