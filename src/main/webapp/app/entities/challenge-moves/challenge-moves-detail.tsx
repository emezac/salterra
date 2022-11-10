import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './challenge-moves.reducer';

export const ChallengeMovesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const challengeMovesEntity = useAppSelector(state => state.challengeMoves.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="challengeMovesDetailsHeading">Challenge Moves</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{challengeMovesEntity.id}</dd>
          <dt>
            <span id="moveDate">Move Date</span>
          </dt>
          <dd>
            {challengeMovesEntity.moveDate ? (
              <TextFormat value={challengeMovesEntity.moveDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Challenge</dt>
          <dd>{challengeMovesEntity.challenge ? challengeMovesEntity.challenge.id : ''}</dd>
          <dt>Move</dt>
          <dd>{challengeMovesEntity.move ? challengeMovesEntity.move.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/challenge-moves" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/challenge-moves/${challengeMovesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChallengeMovesDetail;
