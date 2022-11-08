import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDungeon } from 'app/shared/model/dungeon.model';
import { getEntities as getDungeons } from 'app/entities/dungeon/dungeon.reducer';
import { IFloor } from 'app/shared/model/floor.model';
import { getEntities as getFloors } from 'app/entities/floor/floor.reducer';
import { IDungeonFloors } from 'app/shared/model/dungeon-floors.model';
import { getEntity, updateEntity, createEntity, reset } from './dungeon-floors.reducer';

export const DungeonFloorsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dungeons = useAppSelector(state => state.dungeon.entities);
  const floors = useAppSelector(state => state.floor.entities);
  const dungeonFloorsEntity = useAppSelector(state => state.dungeonFloors.entity);
  const loading = useAppSelector(state => state.dungeonFloors.loading);
  const updating = useAppSelector(state => state.dungeonFloors.updating);
  const updateSuccess = useAppSelector(state => state.dungeonFloors.updateSuccess);

  const handleClose = () => {
    navigate('/dungeon-floors');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDungeons({}));
    dispatch(getFloors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...dungeonFloorsEntity,
      ...values,
      dungeon: dungeons.find(it => it.id.toString() === values.dungeon.toString()),
      floor: floors.find(it => it.id.toString() === values.floor.toString()),
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
          ...dungeonFloorsEntity,
          dungeon: dungeonFloorsEntity?.dungeon?.id,
          floor: dungeonFloorsEntity?.floor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.dungeonFloors.home.createOrEditLabel" data-cy="DungeonFloorsCreateUpdateHeading">
            Create or edit a Dungeon Floors
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
                <ValidatedField name="id" required readOnly id="dungeon-floors-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField id="dungeon-floors-dungeon" name="dungeon" data-cy="dungeon" label="Dungeon" type="select">
                <option value="" key="0" />
                {dungeons
                  ? dungeons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="dungeon-floors-floor" name="floor" data-cy="floor" label="Floor" type="select">
                <option value="" key="0" />
                {floors
                  ? floors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dungeon-floors" replace color="info">
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

export default DungeonFloorsUpdate;
