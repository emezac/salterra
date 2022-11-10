import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Choice from './choice';
import ChoiceDetail from './choice-detail';
import ChoiceUpdate from './choice-update';
import ChoiceDeleteDialog from './choice-delete-dialog';

const ChoiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Choice />} />
    <Route path="new" element={<ChoiceUpdate />} />
    <Route path=":id">
      <Route index element={<ChoiceDetail />} />
      <Route path="edit" element={<ChoiceUpdate />} />
      <Route path="delete" element={<ChoiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChoiceRoutes;
