import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './room-connection.reducer';

export const RoomConnectionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const roomConnectionEntity = useAppSelector(state => state.roomConnection.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roomConnectionDetailsHeading">Room Connection</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{roomConnectionEntity.id}</dd>
          <dt>
            <span id="room1">Room 1</span>
          </dt>
          <dd>{roomConnectionEntity.room1}</dd>
          <dt>
            <span id="room2">Room 2</span>
          </dt>
          <dd>{roomConnectionEntity.room2}</dd>
          <dt>
            <span id="room3">Room 3</span>
          </dt>
          <dd>{roomConnectionEntity.room3}</dd>
          <dt>
            <span id="room4">Room 4</span>
          </dt>
          <dd>{roomConnectionEntity.room4}</dd>
          <dt>
            <span id="room5">Room 5</span>
          </dt>
          <dd>{roomConnectionEntity.room5}</dd>
          <dt>
            <span id="room6">Room 6</span>
          </dt>
          <dd>{roomConnectionEntity.room6}</dd>
          <dt>
            <span id="room7">Room 7</span>
          </dt>
          <dd>{roomConnectionEntity.room7}</dd>
          <dt>
            <span id="room8">Room 8</span>
          </dt>
          <dd>{roomConnectionEntity.room8}</dd>
          <dt>
            <span id="room9">Room 9</span>
          </dt>
          <dd>{roomConnectionEntity.room9}</dd>
          <dt>
            <span id="room10">Room 10</span>
          </dt>
          <dd>{roomConnectionEntity.room10}</dd>
          <dt>
            <span id="room11">Room 11</span>
          </dt>
          <dd>{roomConnectionEntity.room11}</dd>
          <dt>
            <span id="room12">Room 12</span>
          </dt>
          <dd>{roomConnectionEntity.room12}</dd>
          <dt>
            <span id="room13">Room 13</span>
          </dt>
          <dd>{roomConnectionEntity.room13}</dd>
          <dt>
            <span id="room14">Room 14</span>
          </dt>
          <dd>{roomConnectionEntity.room14}</dd>
          <dt>
            <span id="room15">Room 15</span>
          </dt>
          <dd>{roomConnectionEntity.room15}</dd>
          <dt>
            <span id="room16">Room 16</span>
          </dt>
          <dd>{roomConnectionEntity.room16}</dd>
          <dt>
            <span id="room17">Room 17</span>
          </dt>
          <dd>{roomConnectionEntity.room17}</dd>
          <dt>
            <span id="room18">Room 18</span>
          </dt>
          <dd>{roomConnectionEntity.room18}</dd>
          <dt>
            <span id="room19">Room 19</span>
          </dt>
          <dd>{roomConnectionEntity.room19}</dd>
          <dt>
            <span id="room20">Room 20</span>
          </dt>
          <dd>{roomConnectionEntity.room20}</dd>
          <dt>
            <span id="room21">Room 21</span>
          </dt>
          <dd>{roomConnectionEntity.room21}</dd>
          <dt>
            <span id="room22">Room 22</span>
          </dt>
          <dd>{roomConnectionEntity.room22}</dd>
          <dt>
            <span id="room23">Room 23</span>
          </dt>
          <dd>{roomConnectionEntity.room23}</dd>
          <dt>
            <span id="room24">Room 24</span>
          </dt>
          <dd>{roomConnectionEntity.room24}</dd>
          <dt>
            <span id="room25">Room 25</span>
          </dt>
          <dd>{roomConnectionEntity.room25}</dd>
          <dt>
            <span id="room26">Room 26</span>
          </dt>
          <dd>{roomConnectionEntity.room26}</dd>
          <dt>
            <span id="room27">Room 27</span>
          </dt>
          <dd>{roomConnectionEntity.room27}</dd>
          <dt>
            <span id="room28">Room 28</span>
          </dt>
          <dd>{roomConnectionEntity.room28}</dd>
          <dt>
            <span id="room29">Room 29</span>
          </dt>
          <dd>{roomConnectionEntity.room29}</dd>
          <dt>
            <span id="room30">Room 30</span>
          </dt>
          <dd>{roomConnectionEntity.room30}</dd>
          <dt>
            <span id="room31">Room 31</span>
          </dt>
          <dd>{roomConnectionEntity.room31}</dd>
          <dt>
            <span id="room32">Room 32</span>
          </dt>
          <dd>{roomConnectionEntity.room32}</dd>
          <dt>
            <span id="room33">Room 33</span>
          </dt>
          <dd>{roomConnectionEntity.room33}</dd>
          <dt>
            <span id="room34">Room 34</span>
          </dt>
          <dd>{roomConnectionEntity.room34}</dd>
          <dt>
            <span id="room35">Room 35</span>
          </dt>
          <dd>{roomConnectionEntity.room35}</dd>
          <dt>Room</dt>
          <dd>{roomConnectionEntity.room ? roomConnectionEntity.room.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/room-connection" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room-connection/${roomConnectionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomConnectionDetail;
