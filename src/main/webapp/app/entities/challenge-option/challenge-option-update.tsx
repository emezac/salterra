import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivity } from 'app/shared/model/activity.model';
import { getEntities as getActivities } from 'app/entities/activity/activity.reducer';
import { IChallengeOption } from 'app/shared/model/challenge-option.model';
import { getEntity, updateEntity, createEntity, reset } from './challenge-option.reducer';

export const ChallengeOptionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activities = useAppSelector(state => state.activity.entities);
  const challengeOptionEntity = useAppSelector(state => state.challengeOption.entity);
  const loading = useAppSelector(state => state.challengeOption.loading);
  const updating = useAppSelector(state => state.challengeOption.updating);
  const updateSuccess = useAppSelector(state => state.challengeOption.updateSuccess);

  const handleClose = () => {
    navigate('/challenge-option');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getActivities({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...challengeOptionEntity,
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
      ? {}
      : {
          ...challengeOptionEntity,
          activity: challengeOptionEntity?.activity?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.challengeOption.home.createOrEditLabel" data-cy="ChallengeOptionCreateUpdateHeading">
            Create or edit a Challenge Option
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
                <ValidatedField name="id" required readOnly id="challenge-option-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Option Name" id="challenge-option-optionName" name="optionName" data-cy="optionName" type="text" />
              <ValidatedField label="Option" id="challenge-option-option" name="option" data-cy="option" type="text" />
              <ValidatedField id="challenge-option-activity" name="activity" data-cy="activity" label="Activity" type="select">
                <option value="" key="0" />
                {activities
                  ? activities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/challenge-option" replace color="info">
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

export default ChallengeOptionUpdate;
