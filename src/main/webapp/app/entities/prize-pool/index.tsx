import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PrizePool from './prize-pool';
import PrizePoolDetail from './prize-pool-detail';
import PrizePoolUpdate from './prize-pool-update';
import PrizePoolDeleteDialog from './prize-pool-delete-dialog';

const PrizePoolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PrizePool />} />
    <Route path="new" element={<PrizePoolUpdate />} />
    <Route path=":id">
      <Route index element={<PrizePoolDetail />} />
      <Route path="edit" element={<PrizePoolUpdate />} />
      <Route path="delete" element={<PrizePoolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PrizePoolRoutes;
