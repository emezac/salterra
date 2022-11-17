import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity.reducer';

export const ActivityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activityEntity = useAppSelector(state => state.activity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activityDetailsHeading">Activity</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{activityEntity.id}</dd>
          <dt>
            <span id="successText">Success Text</span>
          </dt>
          <dd>{activityEntity.successText}</dd>
          <dt>
            <span id="failureText">Failure Text</span>
          </dt>
          <dd>{activityEntity.failureText}</dd>
          <dt>
            <span id="skipText">Skip Text</span>
          </dt>
          <dd>{activityEntity.skipText}</dd>
          <dt>
            <span id="challangeDifficulty">Challange Difficulty</span>
          </dt>
          <dd>{activityEntity.challangeDifficulty}</dd>
          <dt>
            <span id="challengeType">Challenge Type</span>
          </dt>
          <dd>{activityEntity.challengeType}</dd>
          <dt>
            <span id="failureResult">Failure Result</span>
          </dt>
          <dd>{activityEntity.failureResult}</dd>
          <dt>
            <span id="successResult">Success Result</span>
          </dt>
          <dd>{activityEntity.successResult}</dd>
          <dt>
            <span id="skipResult">Skip Result</span>
          </dt>
          <dd>{activityEntity.skipResult}</dd>
          <dt>
            <span id="sW">S W</span>
          </dt>
          <dd>{activityEntity.sW}</dd>
          <dt>
            <span id="e">E</span>
          </dt>
          <dd>{activityEntity.e}</dd>
          <dt>
            <span id="nW">N W</span>
          </dt>
          <dd>{activityEntity.nW}</dd>
          <dt>
            <span id="n">N</span>
          </dt>
          <dd>{activityEntity.n}</dd>
          <dt>
            <span id="sE">S E</span>
          </dt>
          <dd>{activityEntity.sE}</dd>
          <dt>
            <span id="s">S</span>
          </dt>
          <dd>{activityEntity.s}</dd>
          <dt>
            <span id="nE">N E</span>
          </dt>
          <dd>{activityEntity.nE}</dd>
          <dt>
            <span id="w">W</span>
          </dt>
          <dd>{activityEntity.w}</dd>
          <dt>Room</dt>
          <dd>{activityEntity.room ? activityEntity.room.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/activity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity/${activityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivityDetail;
