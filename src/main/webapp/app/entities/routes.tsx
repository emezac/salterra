import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Move from './move';
import Prize from './prize';
import Profile from './profile';
import Card from './card';
import Room from './room';
import Choice from './choice';
import Option from './option';
import PrizePool from './prize-pool';
import Activity from './activity';
import GameStatus from './game-status';
import ActivityMoves from './activity-moves';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="move/*" element={<Move />} />
        <Route path="prize/*" element={<Prize />} />
        <Route path="profile/*" element={<Profile />} />
        <Route path="card/*" element={<Card />} />
        <Route path="room/*" element={<Room />} />
        <Route path="choice/*" element={<Choice />} />
        <Route path="option/*" element={<Option />} />
        <Route path="prize-pool/*" element={<PrizePool />} />
        <Route path="activity/*" element={<Activity />} />
        <Route path="game-status/*" element={<GameStatus />} />
        <Route path="activity-moves/*" element={<ActivityMoves />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
