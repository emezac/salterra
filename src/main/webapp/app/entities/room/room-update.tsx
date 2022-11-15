import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRoom } from 'app/shared/model/room.model';
import { getEntity, updateEntity, createEntity, reset } from './room.reducer';

export const RoomUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

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
              {!isNew ? <ValidatedField name="id" required readOnly id="room-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Intro Text" id="room-introText" name="introText" data-cy="introText" type="text" />
              <ValidatedField
                label="Have Gateway Door"
                id="room-haveGatewayDoor"
                name="haveGatewayDoor"
                data-cy="haveGatewayDoor"
                check
                type="checkbox"
              />
              <ValidatedField label="Gwy Door 1" id="room-gwyDoor1" name="gwyDoor1" data-cy="gwyDoor1" type="text" />
              <ValidatedField label="Gwy Door 2" id="room-gwyDoor2" name="gwyDoor2" data-cy="gwyDoor2" type="text" />
              <ValidatedField label="Gwy Door 3" id="room-gwyDoor3" name="gwyDoor3" data-cy="gwyDoor3" type="text" />
              <ValidatedField label="Gwy Door 4" id="room-gwyDoor4" name="gwyDoor4" data-cy="gwyDoor4" type="text" />
              <ValidatedField label="Gwy Door 5" id="room-gwyDoor5" name="gwyDoor5" data-cy="gwyDoor5" type="text" />
              <ValidatedField label="Gwy Door 6" id="room-gwyDoor6" name="gwyDoor6" data-cy="gwyDoor6" type="text" />
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
