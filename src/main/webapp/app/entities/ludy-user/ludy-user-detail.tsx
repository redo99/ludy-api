import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ludy-user.reducer';
import { ILudyUser } from 'app/shared/model/ludy-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILudyUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LudyUserDetail = (props: ILudyUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ludyUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="ludyApp.ludyUser.detail.title">LudyUser</Translate> [<b>{ludyUserEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="score">
              <Translate contentKey="ludyApp.ludyUser.score">Score</Translate>
            </span>
          </dt>
          <dd>{ludyUserEntity.score}</dd>
          <dt>
            <span id="gems">
              <Translate contentKey="ludyApp.ludyUser.gems">Gems</Translate>
            </span>
          </dt>
          <dd>{ludyUserEntity.gems}</dd>
          <dt>
            <Translate contentKey="ludyApp.ludyUser.user">User</Translate>
          </dt>
          <dd>{ludyUserEntity.userLogin ? ludyUserEntity.userLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/ludy-user" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ludy-user/${ludyUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ ludyUser }: IRootState) => ({
  ludyUserEntity: ludyUser.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LudyUserDetail);
