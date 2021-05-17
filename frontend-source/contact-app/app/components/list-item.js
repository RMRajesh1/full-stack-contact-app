import Component from '@glimmer/component';
import { inject as service } from '@ember/service';

export default class ListItemComponent extends Component {
  @service router;

  rootURL = this.router.rootURL
  defaultImage = this.rootURL + 'assets/images/call-profile.svg'
}
