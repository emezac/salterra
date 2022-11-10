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
import { IPrizePool } from 'app/shared/model/prize-pool.model';
import { getEntity, updateEntity, createEntity, reset } from './prize-pool.reducer';

export const PrizePoolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const challenges = useAppSelector(state => state.challenge.entities);
  const prizePoolEntity = useAppSelector(state => state.prizePool.entity);
  const loading = useAppSelector(state => state.prizePool.loading);
  const updating = useAppSelector(state => state.prizePool.updating);
  const updateSuccess = useAppSelector(state => state.prizePool.updateSuccess);

  const handleClose = () => {
    navigate('/prize-pool');
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
      ...prizePoolEntity,
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
          ...prizePoolEntity,
          challenge: prizePoolEntity?.challenge?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.prizePool.home.createOrEditLabel" data-cy="PrizePoolCreateUpdateHeading">
            Create or edit a Prize Pool
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="prize-pool-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Prize Name" id="prize-pool-prizeName" name="prizeName" data-cy="prizeName" type="text" />
              <ValidatedField id="prize-pool-challenge" name="challenge" data-cy="challenge" label="Challenge" type="select">
                <option value="" key="0" />
                {challenges
                  ? challenges.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/prize-pool" replace color="info">
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

export default PrizePoolUpdate;
