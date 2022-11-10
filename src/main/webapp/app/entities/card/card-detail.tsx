import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './card.reducer';

export const CardDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cardEntity = useAppSelector(state => state.card.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cardDetailsHeading">Card</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{cardEntity.id}</dd>
          <dt>
            <span id="thumbnaiImage">Thumbnai Image</span>
          </dt>
          <dd>
            {cardEntity.thumbnaiImage ? (
              <div>
                {cardEntity.thumbnaiImageContentType ? (
                  <a onClick={openFile(cardEntity.thumbnaiImageContentType, cardEntity.thumbnaiImage)}>
                    <img
                      src={`data:${cardEntity.thumbnaiImageContentType};base64,${cardEntity.thumbnaiImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {cardEntity.thumbnaiImageContentType}, {byteSize(cardEntity.thumbnaiImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="highresImage">Highres Image</span>
          </dt>
          <dd>
            {cardEntity.highresImage ? (
              <div>
                {cardEntity.highresImageContentType ? (
                  <a onClick={openFile(cardEntity.highresImageContentType, cardEntity.highresImage)}>
                    <img
                      src={`data:${cardEntity.highresImageContentType};base64,${cardEntity.highresImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {cardEntity.highresImageContentType}, {byteSize(cardEntity.highresImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{cardEntity.createdAt ? <TextFormat value={cardEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{cardEntity.updatedAt ? <TextFormat value={cardEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>{cardEntity.deletedAt ? <TextFormat value={cardEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/card" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/card/${cardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CardDetail;
