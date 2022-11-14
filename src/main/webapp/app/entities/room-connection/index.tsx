import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RoomConnection from './room-connection';
import RoomConnectionDetail from './room-connection-detail';
import RoomConnectionUpdate from './room-connection-update';
import RoomConnectionDeleteDialog from './room-connection-delete-dialog';

const RoomConnectionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RoomConnection />} />
    <Route path="new" element={<RoomConnectionUpdate />} />
    <Route path=":id">
      <Route index element={<RoomConnectionDetail />} />
      <Route path="edit" element={<RoomConnectionUpdate />} />
      <Route path="delete" element={<RoomConnectionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RoomConnectionRoutes;
