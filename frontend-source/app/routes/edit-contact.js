import Route from '@ember/routing/route';

export default class EditContactRoute extends Route {

  model(params) {
    let contact, number, contact_id;
    contact_id = params.contact_id;
    if (contact_id == 'new') {
      number = this.store.createRecord('number');
      contact = this.store.createRecord('contact');
      number.contact = contact;
      number.type = 0;
    } else {
      contact = this.store.findRecord('contact', contact_id);
      number = this.store.query('number', {
        contact: contact_id
      })
      contact.catch(() => this.transitionTo('edit-contact', 'new') );
    }
    return contact;
  }

}
