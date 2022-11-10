import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICard } from 'app/shared/model/card.model';
import { getEntities } from './card.reducer';

export const Card = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const cardList = useAppSelector(state => state.card.entities);
  const loading = useAppSelector(state => state.card.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="card-heading" data-cy="CardHeading">
        Cards
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/card/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Card
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cardList && cardList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>Id</th>
                <th>Thumbnai Image</th>
                <th>Highres Image</th>
                <th>Created At</th>
                <th>Updated At</th>
                <th>Deleted At</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cardList.map((card, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/card/${card.id}`} color="link" size="sm">
                      {card.id}
                    </Button>
                  </td>
                  <td>
                    {card.thumbnaiImage ? (
                      <div>
                        {card.thumbnaiImageContentType ? (
                          <a onClick={openFile(card.thumbnaiImageContentType, card.thumbnaiImage)}>
                            <img src={`data:${card.thumbnaiImageContentType};base64,${card.thumbnaiImage}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {card.thumbnaiImageContentType}, {byteSize(card.thumbnaiImage)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {card.highresImage ? (
                      <div>
                        {card.highresImageContentType ? (
                          <a onClick={openFile(card.highresImageContentType, card.highresImage)}>
                            <img src={`data:${card.highresImageContentType};base64,${card.highresImage}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {card.highresImageContentType}, {byteSize(card.highresImage)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{card.createdAt ? <TextFormat type="date" value={card.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{card.updatedAt ? <TextFormat type="date" value={card.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{card.deletedAt ? <TextFormat type="date" value={card.deletedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/card/${card.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/card/${card.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/card/${card.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Cards found</div>
        )}
      </div>
    </div>
  );
};

export default Card;
