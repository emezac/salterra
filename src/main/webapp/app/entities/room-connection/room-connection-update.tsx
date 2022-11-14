import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { IRoomConnection } from 'app/shared/model/room-connection.model';
import { getEntity, updateEntity, createEntity, reset } from './room-connection.reducer';

export const RoomConnectionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rooms = useAppSelector(state => state.room.entities);
  const roomConnectionEntity = useAppSelector(state => state.roomConnection.entity);
  const loading = useAppSelector(state => state.roomConnection.loading);
  const updating = useAppSelector(state => state.roomConnection.updating);
  const updateSuccess = useAppSelector(state => state.roomConnection.updateSuccess);

  const handleClose = () => {
    navigate('/room-connection');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRooms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...roomConnectionEntity,
      ...values,
      room: rooms.find(it => it.id.toString() === values.room.toString()),
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
          ...roomConnectionEntity,
          room: roomConnectionEntity?.room?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.roomConnection.home.createOrEditLabel" data-cy="RoomConnectionCreateUpdateHeading">
            Create or edit a Room Connection
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
                <ValidatedField name="id" required readOnly id="room-connection-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Room 1" id="room-connection-room1" name="room1" data-cy="room1" type="text" />
              <ValidatedField label="Room 2" id="room-connection-room2" name="room2" data-cy="room2" type="text" />
              <ValidatedField label="Room 3" id="room-connection-room3" name="room3" data-cy="room3" type="text" />
              <ValidatedField label="Room 4" id="room-connection-room4" name="room4" data-cy="room4" type="text" />
              <ValidatedField label="Room 5" id="room-connection-room5" name="room5" data-cy="room5" type="text" />
              <ValidatedField label="Room 6" id="room-connection-room6" name="room6" data-cy="room6" type="text" />
              <ValidatedField label="Room 7" id="room-connection-room7" name="room7" data-cy="room7" type="text" />
              <ValidatedField label="Room 8" id="room-connection-room8" name="room8" data-cy="room8" type="text" />
              <ValidatedField label="Room 9" id="room-connection-room9" name="room9" data-cy="room9" type="text" />
              <ValidatedField label="Room 10" id="room-connection-room10" name="room10" data-cy="room10" type="text" />
              <ValidatedField label="Room 11" id="room-connection-room11" name="room11" data-cy="room11" type="text" />
              <ValidatedField label="Room 12" id="room-connection-room12" name="room12" data-cy="room12" type="text" />
              <ValidatedField label="Room 13" id="room-connection-room13" name="room13" data-cy="room13" type="text" />
              <ValidatedField label="Room 14" id="room-connection-room14" name="room14" data-cy="room14" type="text" />
              <ValidatedField label="Room 15" id="room-connection-room15" name="room15" data-cy="room15" type="text" />
              <ValidatedField label="Room 16" id="room-connection-room16" name="room16" data-cy="room16" type="text" />
              <ValidatedField label="Room 17" id="room-connection-room17" name="room17" data-cy="room17" type="text" />
              <ValidatedField label="Room 18" id="room-connection-room18" name="room18" data-cy="room18" type="text" />
              <ValidatedField label="Room 19" id="room-connection-room19" name="room19" data-cy="room19" type="text" />
              <ValidatedField label="Room 20" id="room-connection-room20" name="room20" data-cy="room20" type="text" />
              <ValidatedField label="Room 21" id="room-connection-room21" name="room21" data-cy="room21" type="text" />
              <ValidatedField label="Room 22" id="room-connection-room22" name="room22" data-cy="room22" type="text" />
              <ValidatedField label="Room 23" id="room-connection-room23" name="room23" data-cy="room23" type="text" />
              <ValidatedField label="Room 24" id="room-connection-room24" name="room24" data-cy="room24" type="text" />
              <ValidatedField label="Room 25" id="room-connection-room25" name="room25" data-cy="room25" type="text" />
              <ValidatedField label="Room 26" id="room-connection-room26" name="room26" data-cy="room26" type="text" />
              <ValidatedField label="Room 27" id="room-connection-room27" name="room27" data-cy="room27" type="text" />
              <ValidatedField label="Room 28" id="room-connection-room28" name="room28" data-cy="room28" type="text" />
              <ValidatedField label="Room 29" id="room-connection-room29" name="room29" data-cy="room29" type="text" />
              <ValidatedField label="Room 30" id="room-connection-room30" name="room30" data-cy="room30" type="text" />
              <ValidatedField label="Room 31" id="room-connection-room31" name="room31" data-cy="room31" type="text" />
              <ValidatedField label="Room 32" id="room-connection-room32" name="room32" data-cy="room32" type="text" />
              <ValidatedField label="Room 33" id="room-connection-room33" name="room33" data-cy="room33" type="text" />
              <ValidatedField label="Room 34" id="room-connection-room34" name="room34" data-cy="room34" type="text" />
              <ValidatedField label="Room 35" id="room-connection-room35" name="room35" data-cy="room35" type="text" />
              <ValidatedField id="room-connection-room" name="room" data-cy="room" label="Room" type="select">
                <option value="" key="0" />
                {rooms
                  ? rooms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/room-connection" replace color="info">
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

export default RoomConnectionUpdate;
