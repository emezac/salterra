import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './application-user.reducer';

export const ApplicationUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const applicationUserEntity = useAppSelector(state => state.applicationUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicationUserDetailsHeading">Application User</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{applicationUserEntity.id}</dd>
          <dt>
            <span id="fullName">Full Name</span>
          </dt>
          <dd>{applicationUserEntity.fullName}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{applicationUserEntity.email}</dd>
          <dt>
            <span id="password">Password</span>
          </dt>
          <dd>{applicationUserEntity.password}</dd>
          <dt>
            <span id="role">Role</span>
          </dt>
          <dd>{applicationUserEntity.role}</dd>
          <dt>
            <span id="polygonAddress">Polygon Address</span>
          </dt>
          <dd>{applicationUserEntity.polygonAddress}</dd>
          <dt>
            <span id="publicUrl">Public Url</span>
          </dt>
          <dd>{applicationUserEntity.publicUrl}</dd>
          <dt>Event</dt>
          <dd>
            {applicationUserEntity.events
              ? applicationUserEntity.events.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {applicationUserEntity.events && i === applicationUserEntity.events.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/application-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/application-user/${applicationUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicationUserDetail;
