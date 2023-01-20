import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './certificate.reducer';

export const CertificateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const certificateEntity = useAppSelector(state => state.certificate.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="certificateDetailsHeading">Certificate</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{certificateEntity.id}</dd>
          <dt>
            <span id="tokenId">Token Id</span>
          </dt>
          <dd>{certificateEntity.tokenId}</dd>
          <dt>Course</dt>
          <dd>{certificateEntity.course ? certificateEntity.course.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/certificate" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/certificate/${certificateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CertificateDetail;
