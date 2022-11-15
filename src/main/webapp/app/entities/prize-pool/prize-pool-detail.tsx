import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './prize-pool.reducer';

export const PrizePoolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const prizePoolEntity = useAppSelector(state => state.prizePool.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="prizePoolDetailsHeading">Prize Pool</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{prizePoolEntity.id}</dd>
          <dt>
            <span id="prizeName">Prize Name</span>
          </dt>
          <dd>{prizePoolEntity.prizeName}</dd>
          <dt>Activity</dt>
          <dd>{prizePoolEntity.activity ? prizePoolEntity.activity.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/prize-pool" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prize-pool/${prizePoolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PrizePoolDetail;
