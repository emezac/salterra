import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ChallengeOption from './challenge-option';
import ChallengeOptionDetail from './challenge-option-detail';
import ChallengeOptionUpdate from './challenge-option-update';
import ChallengeOptionDeleteDialog from './challenge-option-delete-dialog';

const ChallengeOptionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ChallengeOption />} />
    <Route path="new" element={<ChallengeOptionUpdate />} />
    <Route path=":id">
      <Route index element={<ChallengeOptionDetail />} />
      <Route path="edit" element={<ChallengeOptionUpdate />} />
      <Route path="delete" element={<ChallengeOptionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChallengeOptionRoutes;
