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
              <ValidatedField label="R 1" id="room-connection-r1" name="r1" data-cy="r1" type="text" />
              <ValidatedField label="R 2" id="room-connection-r2" name="r2" data-cy="r2" type="text" />
              <ValidatedField label="R 3" id="room-connection-r3" name="r3" data-cy="r3" type="text" />
              <ValidatedField label="R 4" id="room-connection-r4" name="r4" data-cy="r4" type="text" />
              <ValidatedField label="R 5" id="room-connection-r5" name="r5" data-cy="r5" type="text" />
              <ValidatedField label="R 6" id="room-connection-r6" name="r6" data-cy="r6" type="text" />
              <ValidatedField label="R 7" id="room-connection-r7" name="r7" data-cy="r7" type="text" />
              <ValidatedField label="R 8" id="room-connection-r8" name="r8" data-cy="r8" type="text" />
              <ValidatedField label="R 9" id="room-connection-r9" name="r9" data-cy="r9" type="text" />
              <ValidatedField label="R 10" id="room-connection-r10" name="r10" data-cy="r10" type="text" />
              <ValidatedField label="R 11" id="room-connection-r11" name="r11" data-cy="r11" type="text" />
              <ValidatedField label="R 12" id="room-connection-r12" name="r12" data-cy="r12" type="text" />
              <ValidatedField label="R 13" id="room-connection-r13" name="r13" data-cy="r13" type="text" />
              <ValidatedField label="R 14" id="room-connection-r14" name="r14" data-cy="r14" type="text" />
              <ValidatedField label="R 15" id="room-connection-r15" name="r15" data-cy="r15" type="text" />
              <ValidatedField label="R 16" id="room-connection-r16" name="r16" data-cy="r16" type="text" />
              <ValidatedField label="R 17" id="room-connection-r17" name="r17" data-cy="r17" type="text" />
              <ValidatedField label="R 18" id="room-connection-r18" name="r18" data-cy="r18" type="text" />
              <ValidatedField label="R 19" id="room-connection-r19" name="r19" data-cy="r19" type="text" />
              <ValidatedField label="R 20" id="room-connection-r20" name="r20" data-cy="r20" type="text" />
              <ValidatedField label="R 21" id="room-connection-r21" name="r21" data-cy="r21" type="text" />
              <ValidatedField label="R 22" id="room-connection-r22" name="r22" data-cy="r22" type="text" />
              <ValidatedField label="R 23" id="room-connection-r23" name="r23" data-cy="r23" type="text" />
              <ValidatedField label="R 24" id="room-connection-r24" name="r24" data-cy="r24" type="text" />
              <ValidatedField label="R 25" id="room-connection-r25" name="r25" data-cy="r25" type="text" />
              <ValidatedField label="R 26" id="room-connection-r26" name="r26" data-cy="r26" type="text" />
              <ValidatedField label="R 27" id="room-connection-r27" name="r27" data-cy="r27" type="text" />
              <ValidatedField label="R 28" id="room-connection-r28" name="r28" data-cy="r28" type="text" />
              <ValidatedField label="R 29" id="room-connection-r29" name="r29" data-cy="r29" type="text" />
              <ValidatedField label="R 30" id="room-connection-r30" name="r30" data-cy="r30" type="text" />
              <ValidatedField label="R 31" id="room-connection-r31" name="r31" data-cy="r31" type="text" />
              <ValidatedField label="R 32" id="room-connection-r32" name="r32" data-cy="r32" type="text" />
              <ValidatedField label="R 33" id="room-connection-r33" name="r33" data-cy="r33" type="text" />
              <ValidatedField label="R 34" id="room-connection-r34" name="r34" data-cy="r34" type="text" />
              <ValidatedField label="R 35" id="room-connection-r35" name="r35" data-cy="r35" type="text" />
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
