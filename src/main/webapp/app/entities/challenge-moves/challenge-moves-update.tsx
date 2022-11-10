import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChallenge } from 'app/shared/model/challenge.model';
import { getEntities as getChallenges } from 'app/entities/challenge/challenge.reducer';
import { IMove } from 'app/shared/model/move.model';
import { getEntities as getMoves } from 'app/entities/move/move.reducer';
import { IChallengeMoves } from 'app/shared/model/challenge-moves.model';
import { getEntity, updateEntity, createEntity, reset } from './challenge-moves.reducer';

export const ChallengeMovesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const challenges = useAppSelector(state => state.challenge.entities);
  const moves = useAppSelector(state => state.move.entities);
  const challengeMovesEntity = useAppSelector(state => state.challengeMoves.entity);
  const loading = useAppSelector(state => state.challengeMoves.loading);
  const updating = useAppSelector(state => state.challengeMoves.updating);
  const updateSuccess = useAppSelector(state => state.challengeMoves.updateSuccess);

  const handleClose = () => {
    navigate('/challenge-moves');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getChallenges({}));
    dispatch(getMoves({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.moveDate = convertDateTimeToServer(values.moveDate);

    const entity = {
      ...challengeMovesEntity,
      ...values,
      challenge: challenges.find(it => it.id.toString() === values.challenge.toString()),
      move: moves.find(it => it.id.toString() === values.move.toString()),
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
          ...challengeMovesEntity,
          moveDate: convertDateTimeFromServer(challengeMovesEntity.moveDate),
          challenge: challengeMovesEntity?.challenge?.id,
          move: challengeMovesEntity?.move?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.challengeMoves.home.createOrEditLabel" data-cy="ChallengeMovesCreateUpdateHeading">
            Create or edit a Challenge Moves
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="challenge-moves-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Move Date"
                id="challenge-moves-moveDate"
                name="moveDate"
                data-cy="moveDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="challenge-moves-challenge" name="challenge" data-cy="challenge" label="Challenge" type="select">
                <option value="" key="0" />
                {challenges
                  ? challenges.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="challenge-moves-move" name="move" data-cy="move" label="Move" type="select">
                <option value="" key="0" />
                {moves
                  ? moves.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/challenge-moves" replace color="info">
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

export default ChallengeMovesUpdate;
