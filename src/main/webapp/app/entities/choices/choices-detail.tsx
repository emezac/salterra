import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './choices.reducer';

export const ChoicesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const choicesEntity = useAppSelector(state => state.choices.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="choicesDetailsHeading">Choices</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{choicesEntity.id}</dd>
          <dt>
            <span id="text">Text</span>
          </dt>
          <dd>{choicesEntity.text}</dd>
          <dt>
            <span id="action">Action</span>
          </dt>
          <dd>{choicesEntity.action}</dd>
          <dt>Activity Moves</dt>
          <dd>{choicesEntity.activityMoves ? choicesEntity.activityMoves.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/choices" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/choices/${choicesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChoicesDetail;
