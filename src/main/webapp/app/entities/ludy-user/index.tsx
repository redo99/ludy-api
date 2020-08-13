import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LudyUser from './ludy-user';
import LudyUserDetail from './ludy-user-detail';
import LudyUserUpdate from './ludy-user-update';
import LudyUserDeleteDialog from './ludy-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LudyUserDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LudyUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LudyUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LudyUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={LudyUser} />
    </Switch>
  </>
);

export default Routes;
