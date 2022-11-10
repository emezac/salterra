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
import { IChallenge } from 'app/shared/model/challenge.model';
import { TypesOfChallenge } from 'app/shared/model/enumerations/types-of-challenge.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { getEntity, updateEntity, createEntity, reset } from './challenge.reducer';

export const ChallengeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rooms = useAppSelector(state => state.room.entities);
  const challengeEntity = useAppSelector(state => state.challenge.entity);
  const loading = useAppSelector(state => state.challenge.loading);
  const updating = useAppSelector(state => state.challenge.updating);
  const updateSuccess = useAppSelector(state => state.challenge.updateSuccess);
  const typesOfChallengeValues = Object.keys(TypesOfChallenge);
  const difficultyValues = Object.keys(Difficulty);

  const handleClose = () => {
    navigate('/challenge');
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
      ...challengeEntity,
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
          challengeName: 'STRONG',
          difficulty: 'LOW',
          ...challengeEntity,
          room: challengeEntity?.room?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.challenge.home.createOrEditLabel" data-cy="ChallengeCreateUpdateHeading">
            Create or edit a Challenge
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="challenge-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Intro Text"
                id="challenge-introText"
                name="introText"
                data-cy="introText"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Success Text"
                id="challenge-successText"
                name="successText"
                data-cy="successText"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Failure Text"
                id="challenge-failureText"
                name="failureText"
                data-cy="failureText"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Skip Text" id="challenge-skipText" name="skipText" data-cy="skipText" type="text" />
              <ValidatedField
                label="Challenge Name"
                id="challenge-challengeName"
                name="challengeName"
                data-cy="challengeName"
                type="select"
              >
                {typesOfChallengeValues.map(typesOfChallenge => (
                  <option value={typesOfChallenge} key={typesOfChallenge}>
                    {typesOfChallenge}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Difficulty" id="challenge-difficulty" name="difficulty" data-cy="difficulty" type="select">
                {difficultyValues.map(difficulty => (
                  <option value={difficulty} key={difficulty}>
                    {difficulty}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Prize Number" id="challenge-prizeNumber" name="prizeNumber" data-cy="prizeNumber" type="text" />
              <ValidatedField
                label="Challange Rating"
                id="challenge-challangeRating"
                name="challangeRating"
                data-cy="challangeRating"
                type="text"
              />
              <ValidatedField
                label="Sacrifice Number"
                id="challenge-sacrificeNumber"
                name="sacrificeNumber"
                data-cy="sacrificeNumber"
                type="text"
              />
              <ValidatedField label="Skip Result" id="challenge-skipResult" name="skipResult" data-cy="skipResult" type="text" />
              <ValidatedField id="challenge-room" name="room" data-cy="room" label="Room" type="select">
                <option value="" key="0" />
                {rooms
                  ? rooms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/challenge" replace color="info">
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

export default ChallengeUpdate;
