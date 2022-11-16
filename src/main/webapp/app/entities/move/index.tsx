import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Move from './move';
import MoveDetail from './move-detail';
import MoveUpdate from './move-update';
import MoveDeleteDialog from './move-delete-dialog';

const MoveRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Move />} />
    <Route path="new" element={<MoveUpdate />} />
    <Route path=":id">
      <Route index element={<MoveDetail />} />
      <Route path="edit" element={<MoveUpdate />} />
      <Route path="delete" element={<MoveDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MoveRoutes;
