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
            <span id="introText">Intro Text</span>
          </dt>
          <dd>{activityEntity.introText}</dd>
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
            <span id="typeChallenge">Type Challenge</span>
          </dt>
          <dd>{activityEntity.typeChallenge}</dd>
          <dt>
            <span id="challengeName">Challenge Name</span>
          </dt>
          <dd>{activityEntity.challengeName}</dd>
          <dt>
            <span id="difficulty">Difficulty</span>
          </dt>
          <dd>{activityEntity.difficulty}</dd>
          <dt>
            <span id="challangeRating">Challange Rating</span>
          </dt>
          <dd>{activityEntity.challangeRating}</dd>
          <dt>
            <span id="sacrificeNumber">Sacrifice Number</span>
          </dt>
          <dd>{activityEntity.sacrificeNumber}</dd>
          <dt>
            <span id="skipResult">Skip Result</span>
          </dt>
          <dd>{activityEntity.skipResult}</dd>
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
