package com.tcg.recursivetree;

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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game extends ApplicationAdapter {

    private List<LineSegment> tree;
    private Stack<TreeParam> treeParams;

    private ShapeRenderer sr;

    public static final int VIEWPORT_WIDTH = 1080;
    public static final int VIEWPORT_HEIGHT = 900;

    private Viewport viewport;

    private final float INITIAL_LENGTH = VIEWPORT_HEIGHT * .3f;
    private final float INITIAL_ANGLE = MathUtils.PI / 2.0f;
    private final float DELTA_ANGLE = 30 * MathUtils.degreesToRadians;
    private final float LENGTH_MULT = 0.65f;
    private final float MIN_LENGTH = 1f;

    public final TreeParam[] initialParams = {
            new TreeParam(new Vector2(VIEWPORT_WIDTH * .5f, 0), INITIAL_ANGLE, INITIAL_LENGTH),
    };

    @Override
    public void create() {
        sr = new ShapeRenderer();
        tree = new ArrayList<LineSegment>();
        viewport = new ScreenViewport();
        this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        treeParams = new Stack<TreeParam>();
        for (TreeParam initialParam : initialParams) {
            treeParams.push(initialParam);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        addSingleToTree();

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (int i = 0; i < tree.size(); i++) {
            LineSegment lineSegment = tree.get(i);
//            Color color = new Color(0xD2FFD2FF).lerp(new Color(0xD2D2FFFF), (float) i / tree.size());
//            sr.setColor(color);
            if(lineSegment.getLength() < 5f) {
                sr.setColor(Color.LIME);
            }
            lineSegment.draw(sr);
            sr.setColor(Color.BROWN);
        }
        sr.end();
    }

    private void addSingleToTree() {
        if (!treeParams.isEmpty()) {
            addToTree(treeParams.pop());
        }
    }

    private void addAllToTree() {
        while(!treeParams.isEmpty()) {
            addToTree(treeParams.pop());
        }
    }

    private void addToTree(TreeParam param) {
        if(param.length > MIN_LENGTH) {
            Vector2 p1 = param.startingPos.cpy();
            float x2 = p1.x + (param.length * MathUtils.cos(param.angle));
            float y2 = p1.y + (param.length * MathUtils.sin(param.angle));
            LineSegment segment = LineSegment.of(p1, x2, y2);
            tree.add(segment);
            treeParams.push(new TreeParam(segment.getP2(), param.angle + DELTA_ANGLE, param.length * LENGTH_MULT));
            treeParams.push(new TreeParam(segment.getP2(), param.angle - DELTA_ANGLE, param.length * LENGTH_MULT));
        }
    }

    @Override
    public void dispose() {
        sr.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewport.apply(true);
        viewport.getCamera().position.x = VIEWPORT_WIDTH * .5f;
        viewport.update(width, height);
    }

    static class TreeParam {
        public final Vector2 startingPos;
        public final float angle;
        public final float length;

        public TreeParam(Vector2 startingPos, float angle, float length) {
            this.startingPos = startingPos;
            this.angle = angle;
            this.length = length;
        }
    }

}
