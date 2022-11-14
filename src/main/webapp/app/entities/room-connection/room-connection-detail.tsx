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
            <span id="r1">R 1</span>
          </dt>
          <dd>{roomConnectionEntity.r1}</dd>
          <dt>
            <span id="r2">R 2</span>
          </dt>
          <dd>{roomConnectionEntity.r2}</dd>
          <dt>
            <span id="r3">R 3</span>
          </dt>
          <dd>{roomConnectionEntity.r3}</dd>
          <dt>
            <span id="r4">R 4</span>
          </dt>
          <dd>{roomConnectionEntity.r4}</dd>
          <dt>
            <span id="r5">R 5</span>
          </dt>
          <dd>{roomConnectionEntity.r5}</dd>
          <dt>
            <span id="r6">R 6</span>
          </dt>
          <dd>{roomConnectionEntity.r6}</dd>
          <dt>
            <span id="r7">R 7</span>
          </dt>
          <dd>{roomConnectionEntity.r7}</dd>
          <dt>
            <span id="r8">R 8</span>
          </dt>
          <dd>{roomConnectionEntity.r8}</dd>
          <dt>
            <span id="r9">R 9</span>
          </dt>
          <dd>{roomConnectionEntity.r9}</dd>
          <dt>
            <span id="r10">R 10</span>
          </dt>
          <dd>{roomConnectionEntity.r10}</dd>
          <dt>
            <span id="r11">R 11</span>
          </dt>
          <dd>{roomConnectionEntity.r11}</dd>
          <dt>
            <span id="r12">R 12</span>
          </dt>
          <dd>{roomConnectionEntity.r12}</dd>
          <dt>
            <span id="r13">R 13</span>
          </dt>
          <dd>{roomConnectionEntity.r13}</dd>
          <dt>
            <span id="r14">R 14</span>
          </dt>
          <dd>{roomConnectionEntity.r14}</dd>
          <dt>
            <span id="r15">R 15</span>
          </dt>
          <dd>{roomConnectionEntity.r15}</dd>
          <dt>
            <span id="r16">R 16</span>
          </dt>
          <dd>{roomConnectionEntity.r16}</dd>
          <dt>
            <span id="r17">R 17</span>
          </dt>
          <dd>{roomConnectionEntity.r17}</dd>
          <dt>
            <span id="r18">R 18</span>
          </dt>
          <dd>{roomConnectionEntity.r18}</dd>
          <dt>
            <span id="r19">R 19</span>
          </dt>
          <dd>{roomConnectionEntity.r19}</dd>
          <dt>
            <span id="r20">R 20</span>
          </dt>
          <dd>{roomConnectionEntity.r20}</dd>
          <dt>
            <span id="r21">R 21</span>
          </dt>
          <dd>{roomConnectionEntity.r21}</dd>
          <dt>
            <span id="r22">R 22</span>
          </dt>
          <dd>{roomConnectionEntity.r22}</dd>
          <dt>
            <span id="r23">R 23</span>
          </dt>
          <dd>{roomConnectionEntity.r23}</dd>
          <dt>
            <span id="r24">R 24</span>
          </dt>
          <dd>{roomConnectionEntity.r24}</dd>
          <dt>
            <span id="r25">R 25</span>
          </dt>
          <dd>{roomConnectionEntity.r25}</dd>
          <dt>
            <span id="r26">R 26</span>
          </dt>
          <dd>{roomConnectionEntity.r26}</dd>
          <dt>
            <span id="r27">R 27</span>
          </dt>
          <dd>{roomConnectionEntity.r27}</dd>
          <dt>
            <span id="r28">R 28</span>
          </dt>
          <dd>{roomConnectionEntity.r28}</dd>
          <dt>
            <span id="r29">R 29</span>
          </dt>
          <dd>{roomConnectionEntity.r29}</dd>
          <dt>
            <span id="r30">R 30</span>
          </dt>
          <dd>{roomConnectionEntity.r30}</dd>
          <dt>
            <span id="r31">R 31</span>
          </dt>
          <dd>{roomConnectionEntity.r31}</dd>
          <dt>
            <span id="r32">R 32</span>
          </dt>
          <dd>{roomConnectionEntity.r32}</dd>
          <dt>
            <span id="r33">R 33</span>
          </dt>
          <dd>{roomConnectionEntity.r33}</dd>
          <dt>
            <span id="r34">R 34</span>
          </dt>
          <dd>{roomConnectionEntity.r34}</dd>
          <dt>
            <span id="r35">R 35</span>
          </dt>
          <dd>{roomConnectionEntity.r35}</dd>
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
