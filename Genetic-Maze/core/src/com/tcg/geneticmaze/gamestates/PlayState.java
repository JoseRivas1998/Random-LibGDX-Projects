package com.tcg.geneticmaze.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.geneticmaze.entities.Ground;
import com.tcg.geneticmaze.entities.Level;
import com.tcg.geneticmaze.genetics.Population;
import com.tcg.geneticmaze.managers.LevelsManager;
import com.tcg.lichengine.TCGHelpers;
import com.tcg.lichengine.gamestates.GameState;
import com.tcg.lichengine.graphics.TCGViewport;
import com.tcg.lichengine.managers.TCGGameStateManager;
import com.tcg.lichengine.managers.TCGValues;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/22/19
 */
public class PlayState extends GameState {

    private final boolean DEBUG = false;

    private TCGViewport tcgViewport;
    private Level level;

    private Population population;

    public PlayState(TCGGameStateManager tcgGameStateManager) {
        super(tcgGameStateManager);
    }

    @Override
    protected void init() {
        level = new Level(LevelsManager.getMap("map_01"));
        tcgViewport = new TCGViewport(true);
        population = new Population(
                TCGValues.getInt("population_size"),
                level.getStartingPoint(),
                level.getGoalPoint()
        );
    }

    @Override
    public void handleInput(float dt) {

    }

    @Override
    public void update(float dt) {
        if(population.allDotsDead()) {
            population.calculateFitness();
            population.naturalSelection();
            population.mutate();
        }
        population.update(dt, level);
    }

    @Override
    public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
        level.draw(tcgViewport.getCam());

        renderGameObjects(sb, sr, dt);
        renderUI(sb);
        debugRender(sb, sr, dt);
    }

    private void renderGameObjects(SpriteBatch sb, ShapeRenderer sr, float dt) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(tcgViewport.getProjectionMatrix());

        sr.setColor(TCGValues.color("goal"));
        float radius = TCGValues.getFloat("goal_radius");
        sr.ellipse(
                level.getGoalPoint().x - radius, level.getGoalPoint().y - radius,
                radius * 2, radius * 2);

        population.draw(sb, sr, dt);

        sr.end();
    }

    private void renderUI(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(tcgViewport.getProjectionMatrix());
        sb.end();
    }

    private void debugRender(SpriteBatch sb, ShapeRenderer sr, float dt) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setProjectionMatrix(tcgViewport.getProjectionMatrix());
        sr.setColor(TCGHelpers.randomColor());
        if(DEBUG) {
            for (Ground ground : level) {
                ground.draw(sb, sr, dt);
            }
        }
        sr.setColor(Color.WHITE);
        sr.end();
    }

    @Override
    public void resize(int width, int height) {
        tcgViewport.resize(width, height);
    }

    @Override
    public void dispose() {
        level.dispose();
    }
}
