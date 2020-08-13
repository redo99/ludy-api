import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ludy-user.reducer';
import { ILudyUser } from 'app/shared/model/ludy-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILudyUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LudyUserUpdate = (props: ILudyUserUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { ludyUserEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/ludy-user');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...ludyUserEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ludyApp.ludyUser.home.createOrEditLabel">
            <Translate contentKey="ludyApp.ludyUser.home.createOrEditLabel">Create or edit a LudyUser</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ludyUserEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="ludy-user-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="ludy-user-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="scoreLabel" for="ludy-user-score">
                  <Translate contentKey="ludyApp.ludyUser.score">Score</Translate>
                </Label>
                <AvField id="ludy-user-score" type="string" className="form-control" name="score" />
              </AvGroup>
              <AvGroup>
                <Label id="gemsLabel" for="ludy-user-gems">
                  <Translate contentKey="ludyApp.ludyUser.gems">Gems</Translate>
                </Label>
                <AvField id="ludy-user-gems" type="string" className="form-control" name="gems" />
              </AvGroup>
              <AvGroup>
                <Label for="ludy-user-user">
                  <Translate contentKey="ludyApp.ludyUser.user">User</Translate>
                </Label>
                <AvInput id="ludy-user-user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/ludy-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  ludyUserEntity: storeState.ludyUser.entity,
  loading: storeState.ludyUser.loading,
  updating: storeState.ludyUser.updating,
  updateSuccess: storeState.ludyUser.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LudyUserUpdate);
