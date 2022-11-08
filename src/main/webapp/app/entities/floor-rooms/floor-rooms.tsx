import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFloorRooms } from 'app/shared/model/floor-rooms.model';
import { getEntities } from './floor-rooms.reducer';

export const FloorRooms = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const floorRoomsList = useAppSelector(state => state.floorRooms.entities);
  const loading = useAppSelector(state => state.floorRooms.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="floor-rooms-heading" data-cy="FloorRoomsHeading">
        Floor Rooms
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/floor-rooms/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Floor Rooms
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {floorRoomsList && floorRoomsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Floor</th>
                <th>Room</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {floorRoomsList.map((floorRooms, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/floor-rooms/${floorRooms.id}`} color="link" size="sm">
                      {floorRooms.id}
                    </Button>
                  </td>
                  <td>{floorRooms.floor ? <Link to={`/floor/${floorRooms.floor.id}`}>{floorRooms.floor.id}</Link> : ''}</td>
                  <td>{floorRooms.room ? <Link to={`/room/${floorRooms.room.id}`}>{floorRooms.room.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/floor-rooms/${floorRooms.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/floor-rooms/${floorRooms.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/floor-rooms/${floorRooms.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Floor Rooms found</div>
        )}
      </div>
    </div>
  );
};

export default FloorRooms;
