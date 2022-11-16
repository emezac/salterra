import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './game-status.reducer';

export const GameStatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gameStatusEntity = useAppSelector(state => state.gameStatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gameStatusDetailsHeading">Game Status</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{gameStatusEntity.id}</dd>
          <dt>
            <span id="moveDate">Move Date</span>
          </dt>
          <dd>
            {gameStatusEntity.moveDate ? <TextFormat value={gameStatusEntity.moveDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastRoomVisited">Last Room Visited</span>
          </dt>
          <dd>{gameStatusEntity.lastRoomVisited}</dd>
          <dt>Profile</dt>
          <dd>{gameStatusEntity.profile ? gameStatusEntity.profile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/game-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-status/${gameStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GameStatusDetail;
