import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';

export default class ContactsRoute extends Route {
  @service store;

  model() {
    const user = this.modelFor('application');
    this.store.query('number', { account: user.id });
    return this.store.query('contact', { account: user.id});
  }

}
