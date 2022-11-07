import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFloor } from 'app/shared/model/floor.model';
import { getEntities as getFloors } from 'app/entities/floor/floor.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { IFloorRooms } from 'app/shared/model/floor-rooms.model';
import { getEntity, updateEntity, createEntity, reset } from './floor-rooms.reducer';

export const FloorRoomsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const floors = useAppSelector(state => state.floor.entities);
  const rooms = useAppSelector(state => state.room.entities);
  const floorRoomsEntity = useAppSelector(state => state.floorRooms.entity);
  const loading = useAppSelector(state => state.floorRooms.loading);
  const updating = useAppSelector(state => state.floorRooms.updating);
  const updateSuccess = useAppSelector(state => state.floorRooms.updateSuccess);

  const handleClose = () => {
    navigate('/floor-rooms');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFloors({}));
    dispatch(getRooms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...floorRoomsEntity,
      ...values,
      floor: floors.find(it => it.id.toString() === values.floor.toString()),
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
          ...floorRoomsEntity,
          floor: floorRoomsEntity?.floor?.id,
          room: floorRoomsEntity?.room?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.floorRooms.home.createOrEditLabel" data-cy="FloorRoomsCreateUpdateHeading">
            Create or edit a Floor Rooms
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="floor-rooms-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Floor Id" id="floor-rooms-floorId" name="floorId" data-cy="floorId" type="text" />
              <ValidatedField label="Room Id" id="floor-rooms-roomId" name="roomId" data-cy="roomId" type="text" />
              <ValidatedField id="floor-rooms-floor" name="floor" data-cy="floor" label="Floor" type="select">
                <option value="" key="0" />
                {floors
                  ? floors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="floor-rooms-room" name="room" data-cy="room" label="Room" type="select">
                <option value="" key="0" />
                {rooms
                  ? rooms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/floor-rooms" replace color="info">
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

export default FloorRoomsUpdate;
