import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profile.reducer';

export const ProfileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const profileEntity = useAppSelector(state => state.profile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profileDetailsHeading">Profile</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{profileEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{profileEntity.name}</dd>
          <dt>
            <span id="auth0UserId">Auth 0 User Id</span>
          </dt>
          <dd>{profileEntity.auth0UserId}</dd>
          <dt>
            <span id="socialNetwork">Social Network</span>
          </dt>
          <dd>{profileEntity.socialNetwork}</dd>
          <dt>
            <span id="profileImage">Profile Image</span>
          </dt>
          <dd>
            {profileEntity.profileImage ? (
              <div>
                {profileEntity.profileImageContentType ? (
                  <a onClick={openFile(profileEntity.profileImageContentType, profileEntity.profileImage)}>
                    <img
                      src={`data:${profileEntity.profileImageContentType};base64,${profileEntity.profileImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {profileEntity.profileImageContentType}, {byteSize(profileEntity.profileImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="suspended">Suspended</span>
          </dt>
          <dd>{profileEntity.suspended ? 'true' : 'false'}</dd>
          <dt>
            <span id="suspendedCount">Suspended Count</span>
          </dt>
          <dd>{profileEntity.suspendedCount}</dd>
          <dt>
            <span id="banned">Banned</span>
          </dt>
          <dd>{profileEntity.banned ? 'true' : 'false'}</dd>
          <dt>
            <span id="aclSetup">Acl Setup</span>
          </dt>
          <dd>{profileEntity.aclSetup}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{profileEntity.createdAt ? <TextFormat value={profileEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedAt">Updated At</span>
          </dt>
          <dd>{profileEntity.updatedAt ? <TextFormat value={profileEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="deletedAt">Deleted At</span>
          </dt>
          <dd>{profileEntity.deletedAt ? <TextFormat value={profileEntity.deletedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profile/${profileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfileDetail;
