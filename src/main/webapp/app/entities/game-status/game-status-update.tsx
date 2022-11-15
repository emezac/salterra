import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfile } from 'app/shared/model/profile.model';
import { getEntities as getProfiles } from 'app/entities/profile/profile.reducer';
import { IGameStatus } from 'app/shared/model/game-status.model';
import { getEntity, updateEntity, createEntity, reset } from './game-status.reducer';

export const GameStatusUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const profiles = useAppSelector(state => state.profile.entities);
  const gameStatusEntity = useAppSelector(state => state.gameStatus.entity);
  const loading = useAppSelector(state => state.gameStatus.loading);
  const updating = useAppSelector(state => state.gameStatus.updating);
  const updateSuccess = useAppSelector(state => state.gameStatus.updateSuccess);

  const handleClose = () => {
    navigate('/game-status');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProfiles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.moveDate = convertDateTimeToServer(values.moveDate);

    const entity = {
      ...gameStatusEntity,
      ...values,
      profile: profiles.find(it => it.id.toString() === values.profile.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          moveDate: displayDefaultDateTime(),
        }
      : {
          ...gameStatusEntity,
          moveDate: convertDateTimeFromServer(gameStatusEntity.moveDate),
          profile: gameStatusEntity?.profile?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.gameStatus.home.createOrEditLabel" data-cy="GameStatusCreateUpdateHeading">
            Create or edit a Game Status
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="game-status-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Move Date"
                id="game-status-moveDate"
                name="moveDate"
                data-cy="moveDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Last Room Visited"
                id="game-status-lastRoomVisited"
                name="lastRoomVisited"
                data-cy="lastRoomVisited"
                type="text"
              />
              <ValidatedField id="game-status-profile" name="profile" data-cy="profile" label="Profile" type="select">
                <option value="" key="0" />
                {profiles
                  ? profiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/game-status" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default GameStatusUpdate;
