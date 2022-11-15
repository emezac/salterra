import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRoomConnection } from 'app/shared/model/room-connection.model';
import { getEntities } from './room-connection.reducer';

export const RoomConnection = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const roomConnectionList = useAppSelector(state => state.roomConnection.entities);
  const loading = useAppSelector(state => state.roomConnection.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="room-connection-heading" data-cy="RoomConnectionHeading">
        Room Connections
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/room-connection/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Room Connection
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {roomConnectionList && roomConnectionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>R 1</th>
                <th>R 2</th>
                <th>R 3</th>
                <th>R 4</th>
                <th>R 5</th>
                <th>R 6</th>
                <th>R 7</th>
                <th>R 8</th>
                <th>R 9</th>
                <th>R 10</th>
                <th>R 11</th>
                <th>R 12</th>
                <th>R 13</th>
                <th>R 14</th>
                <th>R 15</th>
                <th>R 16</th>
                <th>R 17</th>
                <th>R 18</th>
                <th>R 19</th>
                <th>R 20</th>
                <th>R 21</th>
                <th>R 22</th>
                <th>R 23</th>
                <th>R 24</th>
                <th>R 25</th>
                <th>R 26</th>
                <th>R 27</th>
                <th>R 28</th>
                <th>R 29</th>
                <th>R 30</th>
                <th>R 31</th>
                <th>R 32</th>
                <th>R 33</th>
                <th>R 34</th>
                <th>R 35</th>
                <th>Room</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {roomConnectionList.map((roomConnection, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/room-connection/${roomConnection.id}`} color="link" size="sm">
                      {roomConnection.id}
                    </Button>
                  </td>
                  <td>{roomConnection.r1}</td>
                  <td>{roomConnection.r2}</td>
                  <td>{roomConnection.r3}</td>
                  <td>{roomConnection.r4}</td>
                  <td>{roomConnection.r5}</td>
                  <td>{roomConnection.r6}</td>
                  <td>{roomConnection.r7}</td>
                  <td>{roomConnection.r8}</td>
                  <td>{roomConnection.r9}</td>
                  <td>{roomConnection.r10}</td>
                  <td>{roomConnection.r11}</td>
                  <td>{roomConnection.r12}</td>
                  <td>{roomConnection.r13}</td>
                  <td>{roomConnection.r14}</td>
                  <td>{roomConnection.r15}</td>
                  <td>{roomConnection.r16}</td>
                  <td>{roomConnection.r17}</td>
                  <td>{roomConnection.r18}</td>
                  <td>{roomConnection.r19}</td>
                  <td>{roomConnection.r20}</td>
                  <td>{roomConnection.r21}</td>
                  <td>{roomConnection.r22}</td>
                  <td>{roomConnection.r23}</td>
                  <td>{roomConnection.r24}</td>
                  <td>{roomConnection.r25}</td>
                  <td>{roomConnection.r26}</td>
                  <td>{roomConnection.r27}</td>
                  <td>{roomConnection.r28}</td>
                  <td>{roomConnection.r29}</td>
                  <td>{roomConnection.r30}</td>
                  <td>{roomConnection.r31}</td>
                  <td>{roomConnection.r32}</td>
                  <td>{roomConnection.r33}</td>
                  <td>{roomConnection.r34}</td>
                  <td>{roomConnection.r35}</td>
                  <td>{roomConnection.room ? <Link to={`/room/${roomConnection.room.id}`}>{roomConnection.room.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/room-connection/${roomConnection.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/room-connection/${roomConnection.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/room-connection/${roomConnection.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Room Connections found</div>
        )}
      </div>
    </div>
  );
};

export default RoomConnection;
