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
import { IChoices } from 'app/shared/model/choices.model';
import { Action } from 'app/shared/model/enumerations/action.model';
import { getEntity, updateEntity, createEntity, reset } from './choices.reducer';

export const ChoicesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activityMoves = useAppSelector(state => state.activityMoves.entities);
  const choicesEntity = useAppSelector(state => state.choices.entity);
  const loading = useAppSelector(state => state.choices.loading);
  const updating = useAppSelector(state => state.choices.updating);
  const updateSuccess = useAppSelector(state => state.choices.updateSuccess);
  const actionValues = Object.keys(Action);

  const handleClose = () => {
    navigate('/choices');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActivityMoves({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...choicesEntity,
      ...values,
      activityMoves: activityMoves.find(it => it.id.toString() === values.activityMoves.toString()),
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
          action: 'MOVE',
          ...choicesEntity,
          activityMoves: choicesEntity?.activityMoves?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.choices.home.createOrEditLabel" data-cy="ChoicesCreateUpdateHeading">
            Create or edit a Choices
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="choices-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Text" id="choices-text" name="text" data-cy="text" type="text" />
              <ValidatedField label="Action" id="choices-action" name="action" data-cy="action" type="select">
                {actionValues.map(action => (
                  <option value={action} key={action}>
                    {action}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="choices-activityMoves" name="activityMoves" data-cy="activityMoves" label="Activity Moves" type="select">
                <option value="" key="0" />
                {activityMoves
                  ? activityMoves.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/choices" replace color="info">
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

export default ChoicesUpdate;
