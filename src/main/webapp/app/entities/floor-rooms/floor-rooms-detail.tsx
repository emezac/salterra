import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './floor-rooms.reducer';

export const FloorRoomsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const floorRoomsEntity = useAppSelector(state => state.floorRooms.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="floorRoomsDetailsHeading">Floor Rooms</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{floorRoomsEntity.id}</dd>
          <dt>
            <span id="floorId">Floor Id</span>
          </dt>
          <dd>{floorRoomsEntity.floorId}</dd>
          <dt>
            <span id="roomId">Room Id</span>
          </dt>
          <dd>{floorRoomsEntity.roomId}</dd>
          <dt>Floor</dt>
          <dd>{floorRoomsEntity.floor ? floorRoomsEntity.floor.id : ''}</dd>
          <dt>Room</dt>
          <dd>{floorRoomsEntity.room ? floorRoomsEntity.room.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/floor-rooms" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/floor-rooms/${floorRoomsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FloorRoomsDetail;
