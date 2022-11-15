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
import { Choice } from 'app/shared/model/enumerations/choice.model';
import { TypesOfChallenge } from 'app/shared/model/enumerations/types-of-challenge.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
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
  const choiceValues = Object.keys(Choice);
  const typesOfChallengeValues = Object.keys(TypesOfChallenge);
  const difficultyValues = Object.keys(Difficulty);

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
          typeChallenge: 'MOVE',
          challengeName: 'STRONG',
          difficulty: 'LOW',
          challangeRating: 'LOW',
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
                label="Intro Text"
                id="activity-introText"
                name="introText"
                data-cy="introText"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
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
              <ValidatedField label="Type Challenge" id="activity-typeChallenge" name="typeChallenge" data-cy="typeChallenge" type="select">
                {choiceValues.map(choice => (
                  <option value={choice} key={choice}>
                    {choice}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Challenge Name" id="activity-challengeName" name="challengeName" data-cy="challengeName" type="select">
                {typesOfChallengeValues.map(typesOfChallenge => (
                  <option value={typesOfChallenge} key={typesOfChallenge}>
                    {typesOfChallenge}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Difficulty" id="activity-difficulty" name="difficulty" data-cy="difficulty" type="select">
                {difficultyValues.map(difficulty => (
                  <option value={difficulty} key={difficulty}>
                    {difficulty}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Challange Rating"
                id="activity-challangeRating"
                name="challangeRating"
                data-cy="challangeRating"
                type="select"
              >
                {difficultyValues.map(difficulty => (
                  <option value={difficulty} key={difficulty}>
                    {difficulty}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Sacrifice Number"
                id="activity-sacrificeNumber"
                name="sacrificeNumber"
                data-cy="sacrificeNumber"
                type="text"
              />
              <ValidatedField label="Skip Result" id="activity-skipResult" name="skipResult" data-cy="skipResult" type="text" />
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
