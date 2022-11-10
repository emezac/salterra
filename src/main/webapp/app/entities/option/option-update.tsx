import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChoice } from 'app/shared/model/choice.model';
import { getEntities as getChoices } from 'app/entities/choice/choice.reducer';
import { IOption } from 'app/shared/model/option.model';
import { getEntity, updateEntity, createEntity, reset } from './option.reducer';

export const OptionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const choices = useAppSelector(state => state.choice.entities);
  const optionEntity = useAppSelector(state => state.option.entity);
  const loading = useAppSelector(state => state.option.loading);
  const updating = useAppSelector(state => state.option.updating);
  const updateSuccess = useAppSelector(state => state.option.updateSuccess);

  const handleClose = () => {
    navigate('/option');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getChoices({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...optionEntity,
      ...values,
      choice: choices.find(it => it.id.toString() === values.choice.toString()),
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
          ...optionEntity,
          choice: optionEntity?.choice?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="salterraApp.option.home.createOrEditLabel" data-cy="OptionCreateUpdateHeading">
            Create or edit a Option
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="option-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Option Name" id="option-optionName" name="optionName" data-cy="optionName" type="text" />
              <ValidatedField id="option-choice" name="choice" data-cy="choice" label="Choice" type="select">
                <option value="" key="0" />
                {choices
                  ? choices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/option" replace color="info">
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

export default OptionUpdate;
