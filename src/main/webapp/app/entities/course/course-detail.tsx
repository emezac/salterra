import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './course.reducer';

export const CourseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const courseEntity = useAppSelector(state => state.course.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="courseDetailsHeading">Course</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{courseEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{courseEntity.name}</dd>
          <dt>
            <span id="tags">Tags</span>
          </dt>
          <dd>{courseEntity.tags}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{courseEntity.description}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>
            {courseEntity.image ? (
              <div>
                {courseEntity.imageContentType ? (
                  <a onClick={openFile(courseEntity.imageContentType, courseEntity.image)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {courseEntity.imageContentType}, {byteSize(courseEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="imageBarCode">Image Bar Code</span>
          </dt>
          <dd>
            {courseEntity.imageBarCode ? (
              <div>
                {courseEntity.imageBarCodeContentType ? (
                  <a onClick={openFile(courseEntity.imageBarCodeContentType, courseEntity.imageBarCode)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {courseEntity.imageBarCodeContentType}, {byteSize(courseEntity.imageBarCode)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="certificateImageTemplate">Certificate Image Template</span>
          </dt>
          <dd>
            {courseEntity.certificateImageTemplate ? (
              <div>
                {courseEntity.certificateImageTemplateContentType ? (
                  <a onClick={openFile(courseEntity.certificateImageTemplateContentType, courseEntity.certificateImageTemplate)}>
                    Open&nbsp;
                  </a>
                ) : null}
                <span>
                  {courseEntity.certificateImageTemplateContentType}, {byteSize(courseEntity.certificateImageTemplate)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Application User</dt>
          <dd>{courseEntity.applicationUser ? courseEntity.applicationUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/course" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course/${courseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CourseDetail;
