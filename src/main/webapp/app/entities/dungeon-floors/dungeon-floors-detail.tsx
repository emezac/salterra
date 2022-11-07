import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './dungeon-floors.reducer';

export const DungeonFloorsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dungeonFloorsEntity = useAppSelector(state => state.dungeonFloors.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dungeonFloorsDetailsHeading">Dungeon Floors</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{dungeonFloorsEntity.id}</dd>
          <dt>
            <span id="dgId">Dg Id</span>
          </dt>
          <dd>{dungeonFloorsEntity.dgId}</dd>
          <dt>
            <span id="floorId">Floor Id</span>
          </dt>
          <dd>{dungeonFloorsEntity.floorId}</dd>
          <dt>Dungeon</dt>
          <dd>{dungeonFloorsEntity.dungeon ? dungeonFloorsEntity.dungeon.id : ''}</dd>
          <dt>Floor</dt>
          <dd>{dungeonFloorsEntity.floor ? dungeonFloorsEntity.floor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/dungeon-floors" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dungeon-floors/${dungeonFloorsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DungeonFloorsDetail;
