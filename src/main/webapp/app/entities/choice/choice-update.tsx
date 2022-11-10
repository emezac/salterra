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
import { IChoice } from 'app/shared/model/choice.model';
import { getEntity, updateEntity, createEntity, reset } from './choice.reducer';

export const ChoiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const challenges = useAppSelector(state => state.challenge.entities);
  const choiceEntity = useAppSelector(state => state.choice.entity);
  const loading = useAppSelector(state => state.choice.loading);
  const updating = useAppSelector(state => state.choice.updating);
  const updateSuccess = useAppSelector(state => state.choice.updateSuccess);

  const handleClose = () => {
    navigate('/choice');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getChallenges({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...choiceEntity,
      ...values,
      challenge: challenges.find(it => it.id.toString() === values.challenge.toString()),
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
          ...choiceEntity,
          challenge: choiceEntity?.challenge?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.choice.home.createOrEditLabel" data-cy="ChoiceCreateUpdateHeading">
            Create or edit a Choice
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="choice-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Choice Name" id="choice-choiceName" name="choiceName" data-cy="choiceName" type="text" />
              <ValidatedField label="Action" id="choice-action" name="action" data-cy="action" type="text" />
              <ValidatedField label="Text" id="choice-text" name="text" data-cy="text" type="text" />
              <ValidatedField id="choice-challenge" name="challenge" data-cy="challenge" label="Challenge" type="select">
                <option value="" key="0" />
                {challenges
                  ? challenges.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/choice" replace color="info">
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

export default ChoiceUpdate;
