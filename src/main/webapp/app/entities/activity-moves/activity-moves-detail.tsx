import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity-moves.reducer';

export const ActivityMovesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activityMovesEntity = useAppSelector(state => state.activityMoves.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activityMovesDetailsHeading">Activity Moves</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{activityMovesEntity.id}</dd>
          <dt>
            <span id="moveDate">Move Date</span>
          </dt>
          <dd>
            {activityMovesEntity.moveDate ? <TextFormat value={activityMovesEntity.moveDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Activity</dt>
          <dd>{activityMovesEntity.activity ? activityMovesEntity.activity.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/activity-moves" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity-moves/${activityMovesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivityMovesDetail;
