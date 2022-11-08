import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProfileGameStatus from './profile-game-status';
import ProfileGameStatusDetail from './profile-game-status-detail';
import ProfileGameStatusUpdate from './profile-game-status-update';
import ProfileGameStatusDeleteDialog from './profile-game-status-delete-dialog';

const ProfileGameStatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProfileGameStatus />} />
    <Route path="new" element={<ProfileGameStatusUpdate />} />
    <Route path=":id">
      <Route index element={<ProfileGameStatusDetail />} />
      <Route path="edit" element={<ProfileGameStatusUpdate />} />
      <Route path="delete" element={<ProfileGameStatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProfileGameStatusRoutes;
