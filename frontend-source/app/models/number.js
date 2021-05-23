import Model, { attr, belongsTo } from '@ember-data/model';

export default class NumberModel extends Model {

  @belongsTo('contact') contact;
  @belongsTo('user') user;

  @attr('string') number;
  @attr('number') type;

}
