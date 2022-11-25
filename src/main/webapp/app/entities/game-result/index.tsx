import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GameResult from './game-result';
import GameResultDetail from './game-result-detail';
import GameResultUpdate from './game-result-update';
import GameResultDeleteDialog from './game-result-delete-dialog';

const GameResultRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GameResult />} />
    <Route path="new" element={<GameResultUpdate />} />
    <Route path=":id">
      <Route index element={<GameResultDetail />} />
      <Route path="edit" element={<GameResultUpdate />} />
      <Route path="delete" element={<GameResultDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GameResultRoutes;
