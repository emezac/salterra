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
                <th>Room 1</th>
                <th>Room 2</th>
                <th>Room 3</th>
                <th>Room 4</th>
                <th>Room 5</th>
                <th>Room 6</th>
                <th>Room 7</th>
                <th>Room 8</th>
                <th>Room 9</th>
                <th>Room 10</th>
                <th>Room 11</th>
                <th>Room 12</th>
                <th>Room 13</th>
                <th>Room 14</th>
                <th>Room 15</th>
                <th>Room 16</th>
                <th>Room 17</th>
                <th>Room 18</th>
                <th>Room 19</th>
                <th>Room 20</th>
                <th>Room 21</th>
                <th>Room 22</th>
                <th>Room 23</th>
                <th>Room 24</th>
                <th>Room 25</th>
                <th>Room 26</th>
                <th>Room 27</th>
                <th>Room 28</th>
                <th>Room 29</th>
                <th>Room 30</th>
                <th>Room 31</th>
                <th>Room 32</th>
                <th>Room 33</th>
                <th>Room 34</th>
                <th>Room 35</th>
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
                  <td>{roomConnection.room1}</td>
                  <td>{roomConnection.room2}</td>
                  <td>{roomConnection.room3}</td>
                  <td>{roomConnection.room4}</td>
                  <td>{roomConnection.room5}</td>
                  <td>{roomConnection.room6}</td>
                  <td>{roomConnection.room7}</td>
                  <td>{roomConnection.room8}</td>
                  <td>{roomConnection.room9}</td>
                  <td>{roomConnection.room10}</td>
                  <td>{roomConnection.room11}</td>
                  <td>{roomConnection.room12}</td>
                  <td>{roomConnection.room13}</td>
                  <td>{roomConnection.room14}</td>
                  <td>{roomConnection.room15}</td>
                  <td>{roomConnection.room16}</td>
                  <td>{roomConnection.room17}</td>
                  <td>{roomConnection.room18}</td>
                  <td>{roomConnection.room19}</td>
                  <td>{roomConnection.room20}</td>
                  <td>{roomConnection.room21}</td>
                  <td>{roomConnection.room22}</td>
                  <td>{roomConnection.room23}</td>
                  <td>{roomConnection.room24}</td>
                  <td>{roomConnection.room25}</td>
                  <td>{roomConnection.room26}</td>
                  <td>{roomConnection.room27}</td>
                  <td>{roomConnection.room28}</td>
                  <td>{roomConnection.room29}</td>
                  <td>{roomConnection.room30}</td>
                  <td>{roomConnection.room31}</td>
                  <td>{roomConnection.room32}</td>
                  <td>{roomConnection.room33}</td>
                  <td>{roomConnection.room34}</td>
                  <td>{roomConnection.room35}</td>
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
