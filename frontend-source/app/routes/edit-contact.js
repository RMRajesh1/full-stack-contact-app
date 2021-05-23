import Route from '@ember/routing/route';

export default class EditContactRoute extends Route {

  model(params) {
    let contact, number, contact_id;
    contact_id = params.contact_id;
    const user = this.modelFor('application')
    if (contact_id == 'new') {
      number = this.store.createRecord('number');
      contact = this.store.createRecord('contact');
      contact.user = user;
      number.contact = contact;
      number.user = contact.user;
      number.type = 0;
    } else {
      contact = this.store.findRecord('contact', contact_id);
      number = this.store.query('number', {
        contact: contact_id,
        account: user.id
      });
      contact.catch(() => this.transitionTo('edit-contact', 'new') );
    }
    return contact;
  }

}
