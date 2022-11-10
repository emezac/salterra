import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './option.reducer';

export const OptionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const optionEntity = useAppSelector(state => state.option.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="optionDetailsHeading">Option</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{optionEntity.id}</dd>
          <dt>
            <span id="optionName">Option Name</span>
          </dt>
          <dd>{optionEntity.optionName}</dd>
          <dt>Option</dt>
          <dd>{optionEntity.option ? optionEntity.option.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/option" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/option/${optionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OptionDetail;
