import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMyUser } from 'app/shared/model/my-user.model';
import { getEntities as getMyUsers } from 'app/entities/my-user/my-user.reducer';
import { IGameResult } from 'app/shared/model/game-result.model';
import { getEntity, updateEntity, createEntity, reset } from './game-result.reducer';

export const GameResultUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const myUsers = useAppSelector(state => state.myUser.entities);
  const gameResultEntity = useAppSelector(state => state.gameResult.entity);
  const loading = useAppSelector(state => state.gameResult.loading);
  const updating = useAppSelector(state => state.gameResult.updating);
  const updateSuccess = useAppSelector(state => state.gameResult.updateSuccess);

  const handleClose = () => {
    navigate('/game-result');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMyUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...gameResultEntity,
      ...values,
      myUser: myUsers.find(it => it.id.toString() === values.myUser.toString()),
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
          createdAt: displayDefaultDateTime(),
        }
      : {
          ...gameResultEntity,
          createdAt: convertDateTimeFromServer(gameResultEntity.createdAt),
          myUser: gameResultEntity?.myUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.gameResult.home.createOrEditLabel" data-cy="GameResultCreateUpdateHeading">
            Create or edit a Game Result
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="game-result-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Won" id="game-result-won" name="won" data-cy="won" check type="checkbox" />
              <ValidatedField label="Lost" id="game-result-lost" name="lost" data-cy="lost" check type="checkbox" />
              <ValidatedField
                label="Created At"
                id="game-result-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="game-result-myUser" name="myUser" data-cy="myUser" label="My User" type="select">
                <option value="" key="0" />
                {myUsers
                  ? myUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/game-result" replace color="info">
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

export default GameResultUpdate;
