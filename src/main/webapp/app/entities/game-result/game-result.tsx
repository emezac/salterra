import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGameResult } from 'app/shared/model/game-result.model';
import { getEntities } from './game-result.reducer';

export const GameResult = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const gameResultList = useAppSelector(state => state.gameResult.entities);
  const loading = useAppSelector(state => state.gameResult.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="game-result-heading" data-cy="GameResultHeading">
        Game Results
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/game-result/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Game Result
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gameResultList && gameResultList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Won</th>
                <th>Lost</th>
                <th>Created At</th>
                <th>My User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gameResultList.map((gameResult, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/game-result/${gameResult.id}`} color="link" size="sm">
                      {gameResult.id}
                    </Button>
                  </td>
                  <td>{gameResult.won ? 'true' : 'false'}</td>
                  <td>{gameResult.lost ? 'true' : 'false'}</td>
                  <td>{gameResult.createdAt ? <TextFormat type="date" value={gameResult.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{gameResult.myUser ? <Link to={`/my-user/${gameResult.myUser.id}`}>{gameResult.myUser.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/game-result/${gameResult.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/game-result/${gameResult.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/game-result/${gameResult.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Game Results found</div>
        )}
      </div>
    </div>
  );
};

export default GameResult;
