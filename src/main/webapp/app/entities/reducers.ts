import applicationUser from 'app/entities/application-user/application-user.reducer';
import course from 'app/entities/course/course.reducer';
import certificate from 'app/entities/certificate/certificate.reducer';
import event from 'app/entities/event/event.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  applicationUser,
  course,
  certificate,
  event,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
