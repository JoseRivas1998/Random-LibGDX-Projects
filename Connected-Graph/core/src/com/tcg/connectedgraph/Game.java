package com.tcg.connectedgraph;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game extends ApplicationAdapter {

    private List<Vector2> points;
    private List<Edge> edges;
    private Viewport viewport;

    private ShapeRenderer sr;

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    private final int POINTS = 40;

    private int i;
    private int j;

    @Override
    public void create() {
        viewport = new ScreenViewport();
        points = new ArrayList<Vector2>();
        edges = new ArrayList<Edge>();

//        for (int i = 0; i < POINTS; i++) {
//            float angle = ((float) i / POINTS) * MathUtils.PI2;
//            float length = HEIGHT * .5f;
////            points.add(new Vector2(length * MathUtils.cos(angle) + WIDTH * .5f, length * MathUtils.sin(angle) + HEIGHT * .5f));
//            points.add(new Vector2(MathUtils.random(WIDTH), MathUtils.random(HEIGHT)));
//        }



        sr = new ShapeRenderer();

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(i < points.size()) {
            if(j < points.size()) {
                Edge edge = new Edge(points.get(i), points.get(j));
                if(!edges.contains(edge) && !points.get(j).equals(points.get(i))) {
                    edges.add(edge);
                }
                j++;
            } else {
                j = 0;
                i++;
            }
        } else {
            i = 0;
            points.add(new Vector2(MathUtils.random(WIDTH), MathUtils.random(HEIGHT)));
        }

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (Edge edge : edges) {
            edge.draw(sr);
        }
        sr.setColor(Color.RED);
        for (Vector2 point : points) {
            sr.ellipse(point.x - 4, point.y - 4, 8, 8);
        }
        sr.setColor(Color.WHITE);
        sr.end();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewport.apply(true);
    }

    static class Edge {
        public final Vector2 from;
        public final Vector2 to;

        public Edge(Vector2 from, Vector2 to) {
            this.from = new Vector2(from);
            this.to = new Vector2(to);
        }

        public void draw(ShapeRenderer sr) {
            sr.line(from, to);
        }

        @Override
        public boolean equals(Object obj) {
            boolean result;
            if (obj == null || obj.getClass() != this.getClass()) {
                result = false;
            } else {
                Edge other = (Edge) obj;
                result = (to.equals(other.to) && from.equals(other.from)) || (to.equals(other.from) && from.equals(other.to));
            }
            return result;
        }
    }

}
