import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './prize.reducer';

export const PrizeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const prizeEntity = useAppSelector(state => state.prize.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="prizeDetailsHeading">Prize</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{prizeEntity.id}</dd>
          <dt>
            <span id="options">Options</span>
          </dt>
          <dd>{prizeEntity.options}</dd>
          <dt>Profile</dt>
          <dd>{prizeEntity.profile ? prizeEntity.profile.id : ''}</dd>
          <dt>Card</dt>
          <dd>{prizeEntity.card ? prizeEntity.card.id : ''}</dd>
          <dt>Room</dt>
          <dd>{prizeEntity.room ? prizeEntity.room.id : ''}</dd>
          <dt>Prize Pool</dt>
          <dd>{prizeEntity.prizePool ? prizeEntity.prizePool.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/prize" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prize/${prizeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PrizeDetail;
