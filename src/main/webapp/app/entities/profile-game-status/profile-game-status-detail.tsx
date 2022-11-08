import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profile-game-status.reducer';

export const ProfileGameStatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const profileGameStatusEntity = useAppSelector(state => state.profileGameStatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profileGameStatusDetailsHeading">Profile Game Status</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{profileGameStatusEntity.id}</dd>
          <dt>
            <span id="moveDate">Move Date</span>
          </dt>
          <dd>
            {profileGameStatusEntity.moveDate ? (
              <TextFormat value={profileGameStatusEntity.moveDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastRoomVisited">Last Room Visited</span>
          </dt>
          <dd>{profileGameStatusEntity.lastRoomVisited}</dd>
          <dt>Profile</dt>
          <dd>{profileGameStatusEntity.profile ? profileGameStatusEntity.profile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/profile-game-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profile-game-status/${profileGameStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfileGameStatusDetail;
