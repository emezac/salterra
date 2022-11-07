import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DungeonFloors from './dungeon-floors';
import DungeonFloorsDetail from './dungeon-floors-detail';
import DungeonFloorsUpdate from './dungeon-floors-update';
import DungeonFloorsDeleteDialog from './dungeon-floors-delete-dialog';

const DungeonFloorsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DungeonFloors />} />
    <Route path="new" element={<DungeonFloorsUpdate />} />
    <Route path=":id">
      <Route index element={<DungeonFloorsDetail />} />
      <Route path="edit" element={<DungeonFloorsUpdate />} />
      <Route path="delete" element={<DungeonFloorsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DungeonFloorsRoutes;
