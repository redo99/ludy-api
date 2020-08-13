import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameLibrary from './game-library';
import GameLibraryDetail from './game-library-detail';
import GameLibraryUpdate from './game-library-update';
import GameLibraryDeleteDialog from './game-library-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameLibraryDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameLibraryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameLibraryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameLibraryDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameLibrary} />
    </Switch>
  </>
);

export default Routes;
