import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FloorRooms from './floor-rooms';
import FloorRoomsDetail from './floor-rooms-detail';
import FloorRoomsUpdate from './floor-rooms-update';
import FloorRoomsDeleteDialog from './floor-rooms-delete-dialog';

const FloorRoomsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FloorRooms />} />
    <Route path="new" element={<FloorRoomsUpdate />} />
    <Route path=":id">
      <Route index element={<FloorRoomsDetail />} />
      <Route path="edit" element={<FloorRoomsUpdate />} />
      <Route path="delete" element={<FloorRoomsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FloorRoomsRoutes;
