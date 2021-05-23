import Model, { attr, belongsTo, hasMany } from '@ember-data/model';

export default class ContactModel extends Model {
  @belongsTo('user') user;
  @hasMany('number') number;

  @attr('string') image;
  @attr('string') name;
  @attr('string') email;
  @attr('string') description;
  @attr('string') date;
}
