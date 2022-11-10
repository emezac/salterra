import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './challenge-option.reducer';

export const ChallengeOptionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const challengeOptionEntity = useAppSelector(state => state.challengeOption.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="challengeOptionDetailsHeading">Challenge Option</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{challengeOptionEntity.id}</dd>
          <dt>
            <span id="optionName">Option Name</span>
          </dt>
          <dd>{challengeOptionEntity.optionName}</dd>
          <dt>
            <span id="option">Option</span>
          </dt>
          <dd>{challengeOptionEntity.option}</dd>
          <dt>Activity</dt>
          <dd>{challengeOptionEntity.activity ? challengeOptionEntity.activity.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/challenge-option" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/challenge-option/${challengeOptionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChallengeOptionDetail;
