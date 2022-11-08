import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfileGameStatus } from 'app/shared/model/profile-game-status.model';
import { getEntities as getProfileGameStatuses } from 'app/entities/profile-game-status/profile-game-status.reducer';
import { IDungeon } from 'app/shared/model/dungeon.model';
import { getEntity, updateEntity, createEntity, reset } from './dungeon.reducer';

export const DungeonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const profileGameStatuses = useAppSelector(state => state.profileGameStatus.entities);
  const dungeonEntity = useAppSelector(state => state.dungeon.entity);
  const loading = useAppSelector(state => state.dungeon.loading);
  const updating = useAppSelector(state => state.dungeon.updating);
  const updateSuccess = useAppSelector(state => state.dungeon.updateSuccess);

  const handleClose = () => {
    navigate('/dungeon');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProfileGameStatuses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    const entity = {
      ...dungeonEntity,
      ...values,
      profileGameStatus: profileGameStatuses.find(it => it.id.toString() === values.profileGameStatus.toString()),
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
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          ...dungeonEntity,
          startDate: convertDateTimeFromServer(dungeonEntity.startDate),
          endDate: convertDateTimeFromServer(dungeonEntity.endDate),
          profileGameStatus: dungeonEntity?.profileGameStatus?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.dungeon.home.createOrEditLabel" data-cy="DungeonCreateUpdateHeading">
            Create or edit a Dungeon
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="dungeon-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Start Date"
                id="dungeon-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="End Date"
                id="dungeon-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="dungeon-profileGameStatus"
                name="profileGameStatus"
                data-cy="profileGameStatus"
                label="Profile Game Status"
                type="select"
              >
                <option value="" key="0" />
                {profileGameStatuses
                  ? profileGameStatuses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dungeon" replace color="info">
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

export default DungeonUpdate;
