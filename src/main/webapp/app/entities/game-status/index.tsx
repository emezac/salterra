import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GameStatus from './game-status';
import GameStatusDetail from './game-status-detail';
import GameStatusUpdate from './game-status-update';
import GameStatusDeleteDialog from './game-status-delete-dialog';

const GameStatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GameStatus />} />
    <Route path="new" element={<GameStatusUpdate />} />
    <Route path=":id">
      <Route index element={<GameStatusDetail />} />
      <Route path="edit" element={<GameStatusUpdate />} />
      <Route path="delete" element={<GameStatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GameStatusRoutes;
