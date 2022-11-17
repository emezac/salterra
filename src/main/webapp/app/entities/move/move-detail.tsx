import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './move.reducer';

export const MoveDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const moveEntity = useAppSelector(state => state.move.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="moveDetailsHeading">Move</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{moveEntity.id}</dd>
          <dt>
            <span id="moveNumber">Move Number</span>
          </dt>
          <dd>{moveEntity.moveNumber}</dd>
          <dt>
            <span id="moveDate">Move Date</span>
          </dt>
          <dd>{moveEntity.moveDate ? <TextFormat value={moveEntity.moveDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Game Status</dt>
          <dd>{moveEntity.gameStatus ? moveEntity.gameStatus.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/move" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/move/${moveEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MoveDetail;
