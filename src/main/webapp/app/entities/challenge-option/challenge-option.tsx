import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChallengeOption } from 'app/shared/model/challenge-option.model';
import { getEntities } from './challenge-option.reducer';

export const ChallengeOption = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const challengeOptionList = useAppSelector(state => state.challengeOption.entities);
  const loading = useAppSelector(state => state.challengeOption.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="challenge-option-heading" data-cy="ChallengeOptionHeading">
        Challenge Options
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/challenge-option/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Challenge Option
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {challengeOptionList && challengeOptionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Option Name</th>
                <th>Option</th>
                <th>Activity</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {challengeOptionList.map((challengeOption, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/challenge-option/${challengeOption.id}`} color="link" size="sm">
                      {challengeOption.id}
                    </Button>
                  </td>
                  <td>{challengeOption.optionName}</td>
                  <td>{challengeOption.option}</td>
                  <td>
                    {challengeOption.activity ? (
                      <Link to={`/activity/${challengeOption.activity.id}`}>{challengeOption.activity.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/challenge-option/${challengeOption.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/challenge-option/${challengeOption.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/challenge-option/${challengeOption.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Challenge Options found</div>
        )}
      </div>
    </div>
  );
};

export default ChallengeOption;
