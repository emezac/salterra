import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPrize } from 'app/shared/model/prize.model';
import { getEntities as getPrizes } from 'app/entities/prize/prize.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { getEntity, updateEntity, createEntity, reset } from './room.reducer';

export const RoomUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const prizes = useAppSelector(state => state.prize.entities);
  const roomEntity = useAppSelector(state => state.room.entity);
  const loading = useAppSelector(state => state.room.loading);
  const updating = useAppSelector(state => state.room.updating);
  const updateSuccess = useAppSelector(state => state.room.updateSuccess);

  const handleClose = () => {
    navigate('/room');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPrizes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...roomEntity,
      ...values,
      prizes: mapIdList(values.prizes),
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
          ...roomEntity,
          prizes: roomEntity?.prizes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.room.home.createOrEditLabel" data-cy="RoomCreateUpdateHeading">
            Create or edit a Room
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="room-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Intro Text"
                id="room-introText"
                name="introText"
                data-cy="introText"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Prize" id="room-prize" data-cy="prize" type="select" multiple name="prizes">
                <option value="" key="0" />
                {prizes
                  ? prizes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/room" replace color="info">
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

export default RoomUpdate;
