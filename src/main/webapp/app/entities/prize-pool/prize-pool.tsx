import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPrizePool } from 'app/shared/model/prize-pool.model';
import { getEntities } from './prize-pool.reducer';

export const PrizePool = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const prizePoolList = useAppSelector(state => state.prizePool.entities);
  const loading = useAppSelector(state => state.prizePool.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="prize-pool-heading" data-cy="PrizePoolHeading">
        Prize Pools
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/prize-pool/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Prize Pool
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {prizePoolList && prizePoolList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Prize Name</th>
                <th>Challenge</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {prizePoolList.map((prizePool, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/prize-pool/${prizePool.id}`} color="link" size="sm">
                      {prizePool.id}
                    </Button>
                  </td>
                  <td>{prizePool.prizeName}</td>
                  <td>{prizePool.challenge ? <Link to={`/challenge/${prizePool.challenge.id}`}>{prizePool.challenge.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/prize-pool/${prizePool.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/prize-pool/${prizePool.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/prize-pool/${prizePool.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Prize Pools found</div>
        )}
      </div>
    </div>
  );
};

export default PrizePool;
