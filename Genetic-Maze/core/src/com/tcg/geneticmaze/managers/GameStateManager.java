package com.tcg.geneticmaze.managers;

import com.tcg.geneticmaze.gamestates.PlayState;
import com.tcg.geneticmaze.gamestates.States;
import com.tcg.lichengine.managers.TCGGameStateManager;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/22/19
 */
public class GameStateManager extends TCGGameStateManager<States> {

    public GameStateManager(States defaultState) {
        super();
        setState(defaultState);
    }

    @Override
    public void setState(States state) {
        switch (state) {
            case Play:
                this.currentState = new PlayState(this);
                break;
        }
    }
}
