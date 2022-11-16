import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ActivityMoves from './activity-moves';
import ActivityMovesDetail from './activity-moves-detail';
import ActivityMovesUpdate from './activity-moves-update';
import ActivityMovesDeleteDialog from './activity-moves-delete-dialog';

const ActivityMovesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ActivityMoves />} />
    <Route path="new" element={<ActivityMovesUpdate />} />
    <Route path=":id">
      <Route index element={<ActivityMovesDetail />} />
      <Route path="edit" element={<ActivityMovesUpdate />} />
      <Route path="delete" element={<ActivityMovesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActivityMovesRoutes;
