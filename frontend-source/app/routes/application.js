import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';

export default class ApplicationRoute extends Route {
  @service cookie;

  model() {
    const account = this.cookie.getCookie('account');
    if (!account) return
    return this.store.queryRecord('user', { account: account });
  }
}
