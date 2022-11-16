import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivityMoves } from 'app/shared/model/activity-moves.model';
import { getEntities as getActivityMoves } from 'app/entities/activity-moves/activity-moves.reducer';
import { IGameStatus } from 'app/shared/model/game-status.model';
import { getEntities as getGameStatuses } from 'app/entities/game-status/game-status.reducer';
import { IMove } from 'app/shared/model/move.model';
import { getEntity, updateEntity, createEntity, reset } from './move.reducer';

export const MoveUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activityMoves = useAppSelector(state => state.activityMoves.entities);
  const gameStatuses = useAppSelector(state => state.gameStatus.entities);
  const moveEntity = useAppSelector(state => state.move.entity);
  const loading = useAppSelector(state => state.move.loading);
  const updating = useAppSelector(state => state.move.updating);
  const updateSuccess = useAppSelector(state => state.move.updateSuccess);

  const handleClose = () => {
    navigate('/move');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActivityMoves({}));
    dispatch(getGameStatuses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...moveEntity,
      ...values,
      activityMoves: activityMoves.find(it => it.id.toString() === values.activityMoves.toString()),
      gameStatus: gameStatuses.find(it => it.id.toString() === values.gameStatus.toString()),
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
          ...moveEntity,
          activityMoves: moveEntity?.activityMoves?.id,
          gameStatus: moveEntity?.gameStatus?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.move.home.createOrEditLabel" data-cy="MoveCreateUpdateHeading">
            Create or edit a Move
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="move-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Move Number" id="move-moveNumber" name="moveNumber" data-cy="moveNumber" type="text" />
              <ValidatedField id="move-activityMoves" name="activityMoves" data-cy="activityMoves" label="Activity Moves" type="select">
                <option value="" key="0" />
                {activityMoves
                  ? activityMoves.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="move-gameStatus" name="gameStatus" data-cy="gameStatus" label="Game Status" type="select">
                <option value="" key="0" />
                {gameStatuses
                  ? gameStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/move" replace color="info">
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

export default MoveUpdate;
