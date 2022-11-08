import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfileGameStatus } from 'app/shared/model/profile-game-status.model';
import { getEntities } from './profile-game-status.reducer';

export const ProfileGameStatus = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const profileGameStatusList = useAppSelector(state => state.profileGameStatus.entities);
  const loading = useAppSelector(state => state.profileGameStatus.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="profile-game-status-heading" data-cy="ProfileGameStatusHeading">
        Profile Game Statuses
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/profile-game-status/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Profile Game Status
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {profileGameStatusList && profileGameStatusList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Move Date</th>
                <th>Last Room Visited</th>
                <th>Profile</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {profileGameStatusList.map((profileGameStatus, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/profile-game-status/${profileGameStatus.id}`} color="link" size="sm">
                      {profileGameStatus.id}
                    </Button>
                  </td>
                  <td>
                    {profileGameStatus.moveDate ? (
                      <TextFormat type="date" value={profileGameStatus.moveDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{profileGameStatus.lastRoomVisited}</td>
                  <td>
                    {profileGameStatus.profile ? (
                      <Link to={`/profile/${profileGameStatus.profile.id}`}>{profileGameStatus.profile.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/profile-game-status/${profileGameStatus.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/profile-game-status/${profileGameStatus.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/profile-game-status/${profileGameStatus.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Profile Game Statuses found</div>
        )}
      </div>
    </div>
  );
};

export default ProfileGameStatus;
