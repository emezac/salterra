import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './choice.reducer';

export const ChoiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const choiceEntity = useAppSelector(state => state.choice.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="choiceDetailsHeading">Choice</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{choiceEntity.id}</dd>
          <dt>
            <span id="choiceName">Choice Name</span>
          </dt>
          <dd>{choiceEntity.choiceName}</dd>
          <dt>
            <span id="action">Action</span>
          </dt>
          <dd>{choiceEntity.action}</dd>
          <dt>
            <span id="text">Text</span>
          </dt>
          <dd>{choiceEntity.text}</dd>
          <dt>Challenge</dt>
          <dd>{choiceEntity.challenge ? choiceEntity.challenge.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/choice" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/choice/${choiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChoiceDetail;
