import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Choices from './choices';
import ChoicesDetail from './choices-detail';
import ChoicesUpdate from './choices-update';
import ChoicesDeleteDialog from './choices-delete-dialog';

const ChoicesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Choices />} />
    <Route path="new" element={<ChoicesUpdate />} />
    <Route path=":id">
      <Route index element={<ChoicesDetail />} />
      <Route path="edit" element={<ChoicesUpdate />} />
      <Route path="delete" element={<ChoicesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChoicesRoutes;
