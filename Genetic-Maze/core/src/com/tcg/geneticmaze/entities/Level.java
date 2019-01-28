package com.tcg.geneticmaze.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.tcg.geneticmaze.managers.LevelsManager;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/22/19
 */
public class Level implements Disposable, Iterable<Ground> {

    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer renderer;

    private int tileWidth;
    private int tileHeight;

    private Array<Ground> ground;

    private Vector2 startingPoint;
    private Vector2 goalPoint;

    public Level(LevelsManager.LevelMap levelMap) {
        ground = new Array<>();
        tileMap = new TmxMapLoader().load(levelMap.getPath());
        renderer = new OrthogonalTiledMapRenderer(tileMap);
        tileWidth = (int) tileMap.getProperties().get("tilewidth");
        tileHeight = (int) tileMap.getProperties().get("tileheight");
        TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get("obsticles");
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                if(cell == null || cell.getTile() == null) continue;

                float x = tileWidth * col;
                float y = tileHeight * row;

                ground.add(new Ground(x, y, tileWidth, tileHeight));

            }
        }

        startingPoint = new Vector2();
        goalPoint = new Vector2();

        MapLayer startingPointLayer = tileMap.getLayers().get("startingPoint");

        for (MapObject mapObject : startingPointLayer.getObjects()) {
            Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse();
            startingPoint.set(ellipse.x, ellipse.y);
        }

        MapLayer goalPointLayer = tileMap.getLayers().get("goalPoint");

        for (MapObject mapObject : goalPointLayer.getObjects()) {
            Ellipse ellipse = ((EllipseMapObject) mapObject).getEllipse();
            goalPoint.set(ellipse.x, ellipse.y);
        }

    }

    public void draw(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public Vector2 getStartingPoint() {
        return new Vector2(startingPoint);
    }

    public Vector2 getGoalPoint() {
        return new Vector2(goalPoint);
    }

    @Override
    public void forEach(Consumer<? super Ground> consumer) {
        ground.forEach(consumer);
    }

    @Override
    public Iterator<Ground> iterator() {
        return ground.iterator();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        tileMap.dispose();
    }
}
