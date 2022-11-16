import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { IActivity } from 'app/shared/model/activity.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { TypeOfChallenge } from 'app/shared/model/enumerations/type-of-challenge.model';
import { FailureResult } from 'app/shared/model/enumerations/failure-result.model';
import { SuccessResult } from 'app/shared/model/enumerations/success-result.model';
import { SkipResult } from 'app/shared/model/enumerations/skip-result.model';
import { getEntity, updateEntity, createEntity, reset } from './activity.reducer';

export const ActivityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rooms = useAppSelector(state => state.room.entities);
  const activityEntity = useAppSelector(state => state.activity.entity);
  const loading = useAppSelector(state => state.activity.loading);
  const updating = useAppSelector(state => state.activity.updating);
  const updateSuccess = useAppSelector(state => state.activity.updateSuccess);
  const difficultyValues = Object.keys(Difficulty);
  const typeOfChallengeValues = Object.keys(TypeOfChallenge);
  const failureResultValues = Object.keys(FailureResult);
  const successResultValues = Object.keys(SuccessResult);
  const skipResultValues = Object.keys(SkipResult);

  const handleClose = () => {
    navigate('/activity');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRooms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...activityEntity,
      ...values,
      room: rooms.find(it => it.id.toString() === values.room.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          challangeDifficulty: 'EASY',
          challengeType: 'STRONG',
          failureResult: 'ONE_MOVE',
          successResult: 'ONE_MOVE',
          skipResult: 'ONE_MOVE',
          ...activityEntity,
          room: activityEntity?.room?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.activity.home.createOrEditLabel" data-cy="ActivityCreateUpdateHeading">
            Create or edit a Activity
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="activity-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Success Text"
                id="activity-successText"
                name="successText"
                data-cy="successText"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Failure Text"
                id="activity-failureText"
                name="failureText"
                data-cy="failureText"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Skip Text" id="activity-skipText" name="skipText" data-cy="skipText" type="text" />
              <ValidatedField
                label="Challange Difficulty"
                id="activity-challangeDifficulty"
                name="challangeDifficulty"
                data-cy="challangeDifficulty"
                type="select"
              >
                {difficultyValues.map(difficulty => (
                  <option value={difficulty} key={difficulty}>
                    {difficulty}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Challenge Type" id="activity-challengeType" name="challengeType" data-cy="challengeType" type="select">
                {typeOfChallengeValues.map(typeOfChallenge => (
                  <option value={typeOfChallenge} key={typeOfChallenge}>
                    {typeOfChallenge}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Failure Result" id="activity-failureResult" name="failureResult" data-cy="failureResult" type="select">
                {failureResultValues.map(failureResult => (
                  <option value={failureResult} key={failureResult}>
                    {failureResult}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Success Result" id="activity-successResult" name="successResult" data-cy="successResult" type="select">
                {successResultValues.map(successResult => (
                  <option value={successResult} key={successResult}>
                    {successResult}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Skip Result" id="activity-skipResult" name="skipResult" data-cy="skipResult" type="select">
                {skipResultValues.map(skipResult => (
                  <option value={skipResult} key={skipResult}>
                    {skipResult}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="S W" id="activity-sW" name="sW" data-cy="sW" type="text" />
              <ValidatedField label="E" id="activity-e" name="e" data-cy="e" type="text" />
              <ValidatedField label="N W" id="activity-nW" name="nW" data-cy="nW" type="text" />
              <ValidatedField label="N" id="activity-n" name="n" data-cy="n" type="text" />
              <ValidatedField label="S E" id="activity-sE" name="sE" data-cy="sE" type="text" />
              <ValidatedField label="S" id="activity-s" name="s" data-cy="s" type="text" />
              <ValidatedField label="N E" id="activity-nE" name="nE" data-cy="nE" type="text" />
              <ValidatedField label="W" id="activity-w" name="w" data-cy="w" type="text" />
              <ValidatedField id="activity-room" name="room" data-cy="room" label="Room" type="select">
                <option value="" key="0" />
                {rooms
                  ? rooms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity" replace color="info">
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

export default ActivityUpdate;
