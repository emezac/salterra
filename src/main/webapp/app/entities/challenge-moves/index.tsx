import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ChallengeMoves from './challenge-moves';
import ChallengeMovesDetail from './challenge-moves-detail';
import ChallengeMovesUpdate from './challenge-moves-update';
import ChallengeMovesDeleteDialog from './challenge-moves-delete-dialog';

const ChallengeMovesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ChallengeMoves />} />
    <Route path="new" element={<ChallengeMovesUpdate />} />
    <Route path=":id">
      <Route index element={<ChallengeMovesDetail />} />
      <Route path="edit" element={<ChallengeMovesUpdate />} />
      <Route path="delete" element={<ChallengeMovesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChallengeMovesRoutes;
