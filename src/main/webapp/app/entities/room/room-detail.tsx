import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './room.reducer';

export const RoomDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const roomEntity = useAppSelector(state => state.room.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roomDetailsHeading">Room</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{roomEntity.id}</dd>
          <dt>
            <span id="introText">Intro Text</span>
          </dt>
          <dd>{roomEntity.introText}</dd>
          <dt>
            <span id="haveGatewayDoor">Have Gateway Door</span>
          </dt>
          <dd>{roomEntity.haveGatewayDoor ? 'true' : 'false'}</dd>
          <dt>
            <span id="gwyDoor1">Gwy Door 1</span>
          </dt>
          <dd>{roomEntity.gwyDoor1}</dd>
          <dt>
            <span id="gwyDoor2">Gwy Door 2</span>
          </dt>
          <dd>{roomEntity.gwyDoor2}</dd>
          <dt>
            <span id="gwyDoor3">Gwy Door 3</span>
          </dt>
          <dd>{roomEntity.gwyDoor3}</dd>
          <dt>
            <span id="gwyDoor4">Gwy Door 4</span>
          </dt>
          <dd>{roomEntity.gwyDoor4}</dd>
          <dt>
            <span id="gwyDoor5">Gwy Door 5</span>
          </dt>
          <dd>{roomEntity.gwyDoor5}</dd>
          <dt>
            <span id="gwyDoor6">Gwy Door 6</span>
          </dt>
          <dd>{roomEntity.gwyDoor6}</dd>
        </dl>
        <Button tag={Link} to="/room" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room/${roomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomDetail;
