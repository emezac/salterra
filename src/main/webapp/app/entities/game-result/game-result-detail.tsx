import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './game-result.reducer';

export const GameResultDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gameResultEntity = useAppSelector(state => state.gameResult.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gameResultDetailsHeading">Game Result</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gameResultEntity.id}</dd>
          <dt>
            <span id="won">Won</span>
          </dt>
          <dd>{gameResultEntity.won ? 'true' : 'false'}</dd>
          <dt>
            <span id="lost">Lost</span>
          </dt>
          <dd>{gameResultEntity.lost ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {gameResultEntity.createdAt ? <TextFormat value={gameResultEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>My User</dt>
          <dd>{gameResultEntity.myUser ? gameResultEntity.myUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/game-result" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-result/${gameResultEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GameResultDetail;
