import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMove } from 'app/shared/model/move.model';
import { getEntities as getMoves } from 'app/entities/move/move.reducer';
import { IActivity } from 'app/shared/model/activity.model';
import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { IActivityMoves } from 'app/shared/model/activity-moves.model';
import { getEntity, updateEntity, createEntity, reset } from './activity-moves.reducer';

export const ActivityMovesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const moves = useAppSelector(state => state.move.entities);
  const activities = useAppSelector(state => state.activity.entities);
  const activityMovesEntity = useAppSelector(state => state.activityMoves.entity);
  const loading = useAppSelector(state => state.activityMoves.loading);
  const updating = useAppSelector(state => state.activityMoves.updating);
  const updateSuccess = useAppSelector(state => state.activityMoves.updateSuccess);

  const handleClose = () => {
    navigate('/activity-moves');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMoves({}));
    dispatch(getActivities({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.moveDate = convertDateTimeToServer(values.moveDate);

    const entity = {
      ...activityMovesEntity,
      ...values,
      activity: activities.find(it => it.id.toString() === values.activity.toString()),
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
          ...activityMovesEntity,
          moveDate: convertDateTimeFromServer(activityMovesEntity.moveDate),
          activity: activityMovesEntity?.activity?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.activityMoves.home.createOrEditLabel" data-cy="ActivityMovesCreateUpdateHeading">
            Create or edit a Activity Moves
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
                <ValidatedField name="id" required readOnly id="activity-moves-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Move Date"
                id="activity-moves-moveDate"
                name="moveDate"
                data-cy="moveDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="activity-moves-activity" name="activity" data-cy="activity" label="Activity" type="select">
                <option value="" key="0" />
                {activities
                  ? activities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity-moves" replace color="info">
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

export default ActivityMovesUpdate;
