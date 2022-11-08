import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Profile from './profile';
import ProfileGameStatus from './profile-game-status';
import Card from './card';
import Dungeon from './dungeon';
import DungeonFloors from './dungeon-floors';
import Floor from './floor';
import FloorRooms from './floor-rooms';
import Prize from './prize';
import Room from './room';
import Challenge from './challenge';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="profile/*" element={<Profile />} />
        <Route path="profile-game-status/*" element={<ProfileGameStatus />} />
        <Route path="card/*" element={<Card />} />
        <Route path="dungeon/*" element={<Dungeon />} />
        <Route path="dungeon-floors/*" element={<DungeonFloors />} />
        <Route path="floor/*" element={<Floor />} />
        <Route path="floor-rooms/*" element={<FloorRooms />} />
        <Route path="prize/*" element={<Prize />} />
        <Route path="room/*" element={<Room />} />
        <Route path="challenge/*" element={<Challenge />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
