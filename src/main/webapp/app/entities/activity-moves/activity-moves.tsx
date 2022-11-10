import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivityMoves } from 'app/shared/model/activity-moves.model';
import { getEntities } from './activity-moves.reducer';

export const ActivityMoves = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const activityMovesList = useAppSelector(state => state.activityMoves.entities);
  const loading = useAppSelector(state => state.activityMoves.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="activity-moves-heading" data-cy="ActivityMovesHeading">
        Activity Moves
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/activity-moves/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Activity Moves
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {activityMovesList && activityMovesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Move Date</th>
                <th>Activity</th>
                <th>Move</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {activityMovesList.map((activityMoves, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/activity-moves/${activityMoves.id}`} color="link" size="sm">
                      {activityMoves.id}
                    </Button>
                  </td>
                  <td>
                    {activityMoves.moveDate ? <TextFormat type="date" value={activityMoves.moveDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {activityMoves.activity ? <Link to={`/activity/${activityMoves.activity.id}`}>{activityMoves.activity.id}</Link> : ''}
                  </td>
                  <td>{activityMoves.move ? <Link to={`/move/${activityMoves.move.id}`}>{activityMoves.move.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/activity-moves/${activityMoves.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/activity-moves/${activityMoves.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/activity-moves/${activityMoves.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Activity Moves found</div>
        )}
      </div>
    </div>
  );
};

export default ActivityMoves;
