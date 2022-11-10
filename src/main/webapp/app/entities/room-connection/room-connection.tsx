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
                <th>From Id</th>
                <th>To Id</th>
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
                  <td>{roomConnection.fromId}</td>
                  <td>{roomConnection.toId}</td>
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
